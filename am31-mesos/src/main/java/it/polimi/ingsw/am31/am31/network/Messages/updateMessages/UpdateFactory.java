package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.TurnOrder;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.GamesManager;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferCardMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UpdateFactory {
 //PLAYER RELATED UPDATES
    public static PlayerScoresUpdate createPlayerScoresUpdate(Player player){
        return new PlayerScoresUpdate(
                player.getNickname(),
                player.getPrestigePoints(),
                player.getFood()
        );
    }
    public static PlayerTribeUpdate createPlayerTribeUpdate(Player player){
        List<String> ids = player.getTribe().stream().map(Card::getCardId).toList();

        return new PlayerTribeUpdate(
                player.getNickname(),
                ids
        );
    }
    public static PlayerBuildingsUpdate createPlayerBuildingsUpdate(Player player){
        List<String> ids = player.getBuildings().stream().map(Card::getCardId).toList();
        return new PlayerBuildingsUpdate(
                player.getNickname(),
                ids
        );
    }

    //GAME RELATED UPDATES
    public static PlayersListUpdate createPlayersListUpdate(Game game){
        return new PlayersListUpdate(game.getPlayersList().stream().map(p -> new PlayerMessage(p.getNickname(), p.getColor())).toList());
    }
    public static GameRoundStatusUpdate createGameRoundStatusUpdate(Game game){
        return new GameRoundStatusUpdate(game.getRoundNumber(), game.getCurrentRoundPhase(), game.getEra());
    }


    public static ShowLobbyUpdate createShowLobbyUpdate(GamesManager gamesManager){

        Set<Map.Entry<Integer, GameController>> activeGames = gamesManager.getActiveGames();
        List<GameController> games= activeGames.stream().filter(game -> game.getValue().getNumPlayers() != game.getValue().getNumActivePlayers()).map(entry -> entry.getValue()).toList();
        List<LobbyDescriptor> lobbies = new ArrayList<>();
        games.forEach( g -> lobbies.add(new LobbyDescriptor(g.getGameID(), g.getNumPlayers(), g.getNumPlayers()-g.getNumActivePlayers())));
        return new ShowLobbyUpdate(lobbies);
    }
    public static GameStartUpdate createGameStartUpdate(){
        return new GameStartUpdate();
    }

    public static EndGameUpdate createGameEndUpdate(List<Player> leaderboard) {return new EndGameUpdate(leaderboard.stream().map(p -> new PlayerMessage(p.getNickname(), p.getColor())).toList());}

    //BOARD RELATED UPDATES
    public static OfferTrackUpdate createOfferTrackUpdate(Board board){
        List<OfferCard> offerTrack = board.getOfferCards();
        List<OfferCardMessage> offerTrackMessages = new ArrayList<OfferCardMessage>();

        for(OfferCard c : offerTrack){
            String nickname = c.isFree() ? OfferCardMessage.EMPTY_CARD : c.getPlayer().getNickname();
            offerTrackMessages.add(new OfferCardMessage(c.getOfferCardId(), nickname, c.isFree()));
        }

        return new OfferTrackUpdate(offerTrackMessages);

    }
    public static CardLineUpdate createCardLineUpdate(Board board, BoardRows row){

        List<String> cardIds;
        if(row == BoardRows.UPPER) cardIds = board.getUpperLine().stream().map(Card::getCardId).toList();
        else cardIds = board.getUnderLine().stream().map(Card::getCardId).toList();
        return new CardLineUpdate(cardIds, row);

    }

    public static TurnOrderUpdate createTurnOrderUpdate(TurnOrder turnOrder){
        List<PlayerMessage> newTurnOrder = turnOrder.getOrder().stream()
                .map(p -> p == null ? null : new PlayerMessage(p.getNickname(), p.getColor()))
                .toList();
        return new TurnOrderUpdate(newTurnOrder);
    }


    public static GameCrashUpdate createGameCrashUpdate() {
        return new GameCrashUpdate();
    }

    public static SuccessRegistrationUpdate createSuccessRegistrationUpdate(String username){
        return new SuccessRegistrationUpdate(username);
    }

}
