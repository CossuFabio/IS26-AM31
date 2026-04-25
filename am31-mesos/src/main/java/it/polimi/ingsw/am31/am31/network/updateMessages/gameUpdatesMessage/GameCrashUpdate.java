package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;


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

}
