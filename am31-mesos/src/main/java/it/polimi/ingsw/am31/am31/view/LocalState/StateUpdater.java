package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;

import java.io.IOException;

public class StateUpdater {
    private LocalGameState gameState;
    private CardMapper mapper;
    //this class maps the update messages on rmiclient/socketclient to localGameState updates
    //counterpart of the UpdateMessages on model side,
    //this is View agnostic, it only changes the state

    public StateUpdater(LocalGameState gameState) {
        this.gameState = gameState;
        //creates a CardMapper, which contains every possible card and a method to
        //get them through their id
        try {
            mapper = new CardMapper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //TODO FINISH HANDLING OF UPDATES: GAME_END,

    //board
    public void HandleUpdateMessage(CardLineUpdate msg){
        //msg contains a list of cards and the row they are in
        //both building and other cards

        //we translate the cardIds, then update the state.
        gameState.setCardLine(mapper.getCards(msg.getCardIds()),msg.getRow());
        //
    }
    public void HandleUpdateMessage(OfferTrackUpdate msg){
        //msg contains the offertrack,with the player inside or free,
        // sent when its freed / set and when game starts

        //mapper creates a list of localofferCards
        gameState.setOfferTrack(mapper.getOfferCards(msg.getOfferTrack()));
        //
    }
    public void HandleUpdateMessage(TurnOrderUpdate msg){
        //msg contains a list of nicknames in order, atm is never sent
        gameState.setTurnOrder(msg.getTurnOrder());
        //
    }

    //game
    public void HandleUpdateMessage(GameRoundStatusUpdate msg){
        //contains a round number and phase, sent when it changes
        gameState.setCurrentRoundPhase(msg.getPhase());
        gameState.setRoundNumber(msg.getRoundNumber());
        gameState.setEra(msg.getEra());
        //
    }
    public void HandleUpdateMessage(GameStartUpdate msg){
        //only changes interface, sent when game starts
        gameState.GameStart();
        //
    }
    public void HandleUpdateMessage(PlayersListUpdate msg){
        //msg contains a list of players with their colors, its sent when a new one is added
        gameState.setPlayers(msg.getPlayersList());
        //
    }
    public void HandleUpdateMessage(ShowLobbyUpdate msg){
            //msg contains a list of lobby descriptors, with their attributes
            //sent on request
        gameState.ShowLobby(msg.getLobbies());
        //
    }

    public void HandleUpdateMessage(EndGameUpdate msg){
        //TODO: IMPLEMENT THIS
        //msg contains winners, for now
        //shows usernames and score (need to create new PlayerMessage class with player.getScore)
    }

    //player
    public void HandleUpdateMessage(PlayerBuildingsUpdate msg){
            //msg contains list of buildingcards and nickname, sent when it changes
        gameState.updatePlayerBuildings(msg.getPlayerId(),mapper.getCards(msg.getBuildingCardsIds()));
    //
    }
    public void HandleUpdateMessage(PlayerScoresUpdate msg){
            //msg contains a nickanme, pp, food for a single player, sent when changed
            gameState.updatePlayerScore(msg.getPlayerId(),msg.getNewFood(),msg.getNewPrestigePoints());
            //
    }
    public void HandleUpdateMessage(PlayerTribeUpdate msg){
            //msg contains the list of tribecards and a nickname, sent when cards change
        //cardIds mapped to List of cards, set to the player
        gameState.updatePlayerTribe(msg.getPlayerId(),mapper.getCards(msg.getTribeCardsId()));
        //
    }
    public void HandleUpdateMessage(GameCrashUpdate msg){
        //TODO: IMPLEMENT THIS
    }
}
