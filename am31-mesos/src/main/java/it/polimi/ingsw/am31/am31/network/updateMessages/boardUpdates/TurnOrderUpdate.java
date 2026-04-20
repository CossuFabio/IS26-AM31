package it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

public class TurnOrderUpdate extends UpdateMessage {

    //TODO: THINK THIS AND HOW TO USE TURNORDER
    @JsonCreator
    public TurnOrderUpdate(){
        super(UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD);
    }

}
