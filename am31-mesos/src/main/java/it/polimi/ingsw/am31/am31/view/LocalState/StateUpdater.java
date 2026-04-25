package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.GameStartUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.PlayersListUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.view.LocalGameState;

public class StateUpdater {
    private LocalGameState gameState;
    //this class maps the update messages on rmiclient/socketclient to localGameState updates
    //counterpart of the UpdateMessages on model side,
    //this is View agnostic, it only changes the state
    public StateUpdater(LocalGameState gameState) {
        this.gameState = gameState;
    }

    //TODO FINISH HANDLING OF UPDATES

    //board
    public void HandleUpdateMessage(CardLineUpdate msg){
        //msg contains a list of cards and the row they are in
        //both building and other cards

    }
    public void HandleUpdateMessage(OfferTrackUpdate msg){
        //msg contains the offertrack,with the player inside or free,
        // sent when its freed / set and when game starts


    }
    public void HandleUpdateMessage(TurnOrderUpdate msg){
        //msg contains... nothing atm, its never sent

    }

    //game
    public void HandleUpdateMessage(GameRoundStatusUpdate msg){
        //contains a round number and phase, sent when it changes

    }
    public void HandleUpdateMessage(GameStartUpdate msg){
        //only changes interface, sent when game starts
        gameState.GameStart();
    }
    public void HandleUpdateMessage(PlayersListUpdate msg){
            //msg contains a list of players, its sent when a new one is added

    }
    public void HandleUpdateMessage(ShowLobbyUpdate msg){
            //msg contains a list of lobby descriptors, with their attributes
            //sent on request
    }

    //player
    public void HandleUpdateMessage(PlayerBuildingsUpdate msg){
            //msg contains list of buildingcards and nickname, sent when it changes
    }
    public void HandleUpdateMessage(PlayerScoresUpdate msg){
            //msg contains a nickanme, pp, food for a single player, sent when changed
    }
    public void HandleUpdateMessage(PlayerTribeUpdate msg){
            //msg contains the list of tribecards and a nickname, sent when cards change
    }
}
