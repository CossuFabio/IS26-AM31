package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateHandler;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerBonusDrawUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;

import java.io.IOException;
import java.util.List;

/**
 * View-side handler for {@link UpdateMessage}s received from the network.
 * <p>
 * Uses the visitor pattern ({@link IUpdateVisitor}) to dispatch each message to the correct
 * overload, resolves String-based ids received from DTOs via {@link LocalResourceTranslator},
 * so it is a component that works across the network and the application layer.
 * If it the translation of resources fails, the update is silently ignored.
 * It writes updates into {@link LocalGameState}, and posts events on the {@link IEventBus} so that View
 * components can react. This class is view-agnostic: it holds no reference to TUI or GUI
 * components. All methods run synchronously on the MessageDispatcher thread.
 * </p>
 */
public class StateUpdater implements IUpdateVisitor, UpdateHandler{
    private final LocalGameState gameState;
    private final LocalResourceTranslator translator;
    private final IEventBus eventBus;

    /**
     * Constructs StateUpdater bound to the given state and event bus.
     * A {@link LocalResourceTranslator} is created internally to resolve card and offer-card IDs.
     *
     * @param gameState the local game state to update on each incoming message
     * @param eventBus  the event bus used to notify listeners after each state change
     * @throws RuntimeException if the translator fails to load card data
     */
    public StateUpdater(LocalGameState gameState, IEventBus eventBus) {
        this.gameState = gameState;
        this.eventBus = eventBus;
        try {
            translator = new LocalResourceTranslator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Entry point for incoming messages; delegates to the correct overload via visitor dispatch.
     *
     * @param m the update message to process
     */
    @Override
    public void handleUpdate(UpdateMessage m){
        m.acceptVisit(this);
    }

    //board

    /**
     * Updates the card line for the row in the message, then posts a {@link BoardUpdateEvent}.
     * Silently skipped if any card ID cannot be resolved.
     */
    @Override
    public void handleUpdateMessage(CardLineUpdate msg){
        try {
            List<Card> cardLine = msg.getCardIds()
                            .stream().map(translator::retrieveCard)
                            .toList();

            gameState.setCardLine(cardLine,msg.getRow());
            eventBus.post(new BoardUpdateEvent());
        } catch (IllegalStateException ignored) {}

    }

    /**
     * Replaces the offer track, then posts a {@link BoardUpdateEvent}.
     * Silently skipped if any card ID cannot be resolved.
     */
    @Override
    public void handleUpdateMessage(OfferTrackUpdate msg){
        try {
            List<LocalOfferCard> newOffers = msg.getOfferTrack().stream()
                    .map(o -> translator.createOfferCard(o.getCardId(), o.isFree(), o.getTotemPlayerNickname()))
                    .toList();
            gameState.setOfferTrack(newOffers);
            eventBus.post(new BoardUpdateEvent());
        } catch (IllegalStateException ignored) {}
    }

    /**
     * Replaces the turn order, preserving null slots, then posts a {@link BoardUpdateEvent}.
     */
    @Override
    public void handleUpdateMessage(TurnOrderUpdate msg){
        List<LocalPlayerState> newPlayers = msg.getTurnOrder().stream()
                .map(pm -> pm == null ? null : new LocalPlayerState(pm.getNickname(), pm.getColor()))
                .toList();
        gameState.setTurnOrder(newPlayers);
        eventBus.post(new BoardUpdateEvent());
    }

    //game

    /**
     * Updates round phase, round number, and era, then posts a {@link BoardUpdateEvent}.
     */
    @Override
    public void handleUpdateMessage(GameRoundStatusUpdate msg){
        gameState.setCurrentRoundPhase(msg.getPhase());
        gameState.setRoundNumber(msg.getRoundNumber());
        gameState.setEra(msg.getEra());
        eventBus.post(new BoardUpdateEvent());
    }

    /**
     * Posts a {@link GameStartingEvent}.
     */
    @Override
    public void handleUpdateMessage(GameStartUpdate msg){
        eventBus.post(new GameStartingEvent());
    }

    /**
     * Replaces the player list, then posts a {@link PlayersInLobbyChangedEvent}.
     */
    @Override
    public void handleUpdateMessage(PlayersListUpdate msg){
        List<LocalPlayerState> newPlayers = msg.getPlayersList().stream().
                map(player -> new LocalPlayerState(player.getNickname(), player.getColor()))
                .toList();

        gameState.setPlayers(newPlayers);
        eventBus.post(new PlayersInLobbyChangedEvent(newPlayers));
    }

    /**
     * Posts a {@link ShowLobbyEvent} with the available lobbies. Does not modify the state.
     */
    @Override
    public void handleUpdateMessage(ShowLobbyUpdate msg){
        List<LobbyDescriptor> lobbies = msg.getLobbies();
        eventBus.post(new ShowLobbyEvent(lobbies));
    }

    /**
     * Records the resolved event card in the state and posts a {@link GameEventResolveEvent}.
     * Silently skipped if the card ID cannot be resolved.
     */
    @Override
    public void handleUpdateMessage(GameEventResolveUpdate msg) {
        try{
            eventBus.post(new GameEventResolveEvent(translator.retrieveCard(msg.getCardId())));
            gameState.addSolvedEvent(translator.retrieveCard(msg.getCardId()));
        }catch(IllegalStateException ignored){}

    }

    /**
     * Sets leaderboard and global ranking in the state, then posts a {@link GameEndedEvent}.
     */
    @Override
    public void handleUpdateMessage(EndGameUpdate msg) {
        List<LocalLeaderBoard> leaderboard = msg.getPlayersLeaderBoard().stream()
                .map(dtoEntry -> new LocalLeaderBoard(
                        gameState.findPlayer(dtoEntry.getPlayerNickname()),
                        dtoEntry.isWinner()))
                .toList();
        gameState.setLeaderboard(leaderboard);
        gameState.setGlobalRanking(msg.getGlobalRanking());
        eventBus.post(new GameEndedEvent());
    }

    //player

    /**
     * Updates the player's building card list, then posts a {@link BoardUpdateEvent}.
     */
    @Override
    public void handleUpdateMessage(PlayerBuildingsUpdate msg){
        List<Card> newBuildings = msg.getBuildingCardsIds()
                .stream()
                .map(translator::retrieveCard)
                .toList();

        gameState.updatePlayerBuildings(msg.getPlayerId(), newBuildings);
        eventBus.post(new BoardUpdateEvent());
    }

    /**
     * Updates the player's food and prestige points, then posts a {@link BoardUpdateEvent}.
     */
    @Override
    public void handleUpdateMessage(PlayerScoresUpdate msg){
        gameState.updatePlayerScore(msg.getPlayerId(),msg.getNewFood(),msg.getNewPrestigePoints());
        eventBus.post(new BoardUpdateEvent());
    }

    /**
     * Updates the player's tribe card list, then posts a {@link BoardUpdateEvent}.
     * Silently skipped if any card ID cannot be resolved.
     */
    @Override
    public void handleUpdateMessage(PlayerTribeUpdate msg){
        try {
            List<Card> newTribe = msg.getTribeCardsIds()
                            .stream()
                            .map(translator::retrieveCard)
                            .toList();

            gameState.updatePlayerTribe(msg.getPlayerId(), newTribe);
            eventBus.post(new BoardUpdateEvent());
        } catch (IllegalStateException ignored) {}
    }

    /**
     * Posts a {@link GameCrashedEvent}.
     */
    @Override
    public void handleUpdateMessage(GameCrashUpdate msg){
        eventBus.post(new GameCrashedEvent());
    }

    /**
     * Posts a {@link SuccessRegistrationEvent} with the registered username.
     */
    @Override
    public void handleUpdateMessage(SuccessRegistrationUpdate msg) {
        eventBus.post(new SuccessRegistrationEvent(msg.getUsername()));
    }

    /**
     * Updates the player's bonus-draw flag. Does not post any event.
     */
    @Override
    public void handleUpdateMessage(PlayerBonusDrawUpdate msg) {
        String playerNickname = msg.getPlayerNickname();
        boolean hasBonusDraw = msg.hasBonusDraw();
        gameState.updatePlayerBonusDraw(playerNickname, hasBonusDraw);
    }
}
