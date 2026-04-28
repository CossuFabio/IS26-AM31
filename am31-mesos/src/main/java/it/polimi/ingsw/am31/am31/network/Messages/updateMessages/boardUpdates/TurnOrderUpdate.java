package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

public class TurnOrderUpdate extends UpdateMessage {

    //TODO: THINK THIS AND HOW TO USE TURNORDER
    @JsonCreator
    public TurnOrderUpdate(){
        super(UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD);
    }

    @Override
    protected boolean checkSpecificValidity() {
        return false;
    }
}
