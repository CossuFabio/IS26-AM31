package it.polimi.ingsw.am31.am31.network.updateMessages;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.GamesManager;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferCardMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.PlayersListUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;

import java.util.ArrayList;
import java.util.List;

public class UpdateFactory {

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


    public static PlayersListUpdate createPlayersListUpdate(Game game){
        return new PlayersListUpdate(game.getPlayersList().stream().map(Player::getNickname).toList());
    }

    public static GameRoundStatusUpdate createGameRoundStatusUpdate(Game game){
        return new GameRoundStatusUpdate(game.getRoundNumber(), game.getCurrentRoundPhase());
    }

    public static ShowLobbyUpdate createShowLobbyUpdate(GamesManager gamesManager){

        List<GameController> activeGames = gamesManager.getActiveGames();
        List<LobbyDescriptor> lobbies = new ArrayList<>();
        for(int i = 0; i<activeGames.size(); i++){
            lobbies.add(new LobbyDescriptor(i, activeGames.get(i).getNumPlayers(), activeGames.get(i).getNumActivePlayers()));
        }
        return new ShowLobbyUpdate(lobbies);
    }


    public static OfferTrackUpdate createOfferTrackUpdate(Board board){
        List<OfferCard> offerTrack = board.getOfferCards();
        List<OfferCardMessage> offerTrackMessages = new ArrayList<OfferCardMessage>();

        for(OfferCard c : offerTrack){
            String nickname = c.isFree() ? null : c.getPlayer().getNickname();
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

    public static TurnOrderUpdate createTurnOrderUpdate(Board board){
        //TODO THINK HOW TO DO THIS - MAYBE CHANGE TURNORDER
        return new TurnOrderUpdate();
    }

}
