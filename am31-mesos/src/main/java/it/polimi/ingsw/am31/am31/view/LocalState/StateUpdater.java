package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateHandler;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;

import java.io.IOException;
import java.util.List;

public class StateUpdater implements IUpdateVisitor, UpdateHandler{
    private final LocalGameState gameState;
    private final CardMapper mapper;

    //When a change occur, this bus is used to notify all interested objects
    private final IEventBus eventBus;

    //this class maps the update messages on rmiclient/socketclient to localGameState updates
    //counterpart of the UpdateMessages on model side,
    //this is View agnostic, it only changes the state

    public StateUpdater(LocalGameState gameState, IEventBus eventBus) {
        this.gameState = gameState;
        this.eventBus = eventBus;
        //creates a CardMapper, which contains every possible card and a method to
        //get them through their id
        try {
            mapper = new CardMapper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void handleUpdate(UpdateMessage m){
        m.acceptVisit(this);
    }


    //board
    @Override
    public void handleUpdateMessage(CardLineUpdate msg){
        //msg contains a list of cards and the row they are in
        //both building and other cards

        //we translate the cardIds, then update the state.
        gameState.setCardLine(mapper.getCards(msg.getCardIds()),msg.getRow());
        //
    }

    @Override
    public void handleUpdateMessage(OfferTrackUpdate msg){
        //msg contains the offertrack,with the player inside or free,
        // sent when its freed / set and when game starts

        //mapper creates a list of localofferCards
        gameState.setOfferTrack(mapper.getOfferCards(msg.getOfferTrack()));
        //
    }

    @Override
    public void handleUpdateMessage(TurnOrderUpdate msg){
        List<LocalPlayerState> newPlayers = msg.getTurnOrder().stream()
                .map(pm -> pm == null ? null : new LocalPlayerState(pm.getNickname(), pm.getColor()))
                .toList();
        gameState.setTurnOrder(newPlayers);
        eventBus.post(new BoardUpdateEvent());
    }

    //game
    @Override
    public void handleUpdateMessage(GameRoundStatusUpdate msg){
        //contains a round number and phase, sent when it changes
        gameState.setCurrentRoundPhase(msg.getPhase());
        gameState.setRoundNumber(msg.getRoundNumber());
        gameState.setEra(msg.getEra());
        eventBus.post(new BoardUpdateEvent());
        //
    }

    @Override
    public void handleUpdateMessage(GameStartUpdate msg){
        eventBus.post(new GameStartingEvent());
    }

    @Override
    public void handleUpdateMessage(PlayersListUpdate msg){
        List<LocalPlayerState> newPlayers = msg.getPlayersList().stream().
                map(player -> new LocalPlayerState(player.getNickname(), player.getColor()))
                .toList();

        gameState.setPlayers(newPlayers);
        eventBus.post(new PlayersInLobbyChangedEvent(newPlayers));
    }

    @Override
    public void handleUpdateMessage(ShowLobbyUpdate msg){
        List<LobbyDescriptor> lobbies = msg.getLobbies();
        eventBus.post(new ShowLobbyEvent(lobbies));
    }

    @Override
    public void handleUpdateMessage(EndGameUpdate msg){
        //TODO: IMPLEMENT THIS
        //msg contains winners, for now
        //shows usernames and score? (need to create new PlayerMessage class with player.getScore)
    }

    //player
    @Override
    public void handleUpdateMessage(PlayerBuildingsUpdate msg){
            //msg contains list of buildingcards and nickname, sent when it changes
        gameState.updatePlayerBuildings(msg.getPlayerId(),mapper.getCards(msg.getBuildingCardsIds()));
        eventBus.post(new BoardUpdateEvent());
        //
    }

    @Override
    public void handleUpdateMessage(PlayerScoresUpdate msg){
            //msg contains a nickanme, pp, food for a single player, sent when changed
            gameState.updatePlayerScore(msg.getPlayerId(),msg.getNewFood(),msg.getNewPrestigePoints());
        eventBus.post(new BoardUpdateEvent());
            //
    }

    @Override
    public void handleUpdateMessage(PlayerTribeUpdate msg){
            //msg contains the list of tribecards and a nickname, sent when cards change
        //cardIds mapped to List of cards, set to the player
        gameState.updatePlayerTribe(msg.getPlayerId(),mapper.getCards(msg.getTribeCardsIds()));
        eventBus.post(new BoardUpdateEvent());
        //
    }

    @Override
    public void handleUpdateMessage(GameCrashUpdate msg){
        //TODO: IMPLEMENT THIS
    }

    @Override
    public void handleUpdateMessage(SuccessRegistrationUpdate msg) {
        eventBus.post(new SuccessRegistrationEvent(msg.getUsername()));
    }
}
