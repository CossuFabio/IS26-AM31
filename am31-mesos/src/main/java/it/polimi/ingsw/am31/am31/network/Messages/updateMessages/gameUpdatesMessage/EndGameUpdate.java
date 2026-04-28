package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;

//Notifies the players that the game has ended and sends the leaderboard
public class EndGameUpdate extends UpdateMessage {

    public EndGameUpdate(){
        super("IMPLEMENT THIS");
    }

    @Override
    protected boolean checkSpecificValidity() {
        return false;
    }
}
