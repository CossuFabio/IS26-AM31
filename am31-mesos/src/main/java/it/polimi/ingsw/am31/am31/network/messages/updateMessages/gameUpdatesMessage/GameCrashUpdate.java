package it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;


/**
 * Notifies the client that the game has been interrupted due to a player disconnection
 */
public class GameCrashUpdate extends UpdateMessage {

    @JsonCreator
    public GameCrashUpdate(){
        super(UpdateMethodsConstants.GAME_CRASHED_METHOD);
    }

    @Override
    protected boolean checkSpecificValidity(){
        return true;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
