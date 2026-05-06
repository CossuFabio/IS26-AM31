package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;


//Thrown when a player is disconnected
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
