package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

//Encapsulates the turnorder as an ordered list of players nicknames. The view will handle how to display it
public class TurnOrderUpdate extends UpdateMessage {

    private final List<String> turnOrder;

    @JsonCreator
    public TurnOrderUpdate(@JsonProperty("turnOrder") List<String> turnOrder){
        super(UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD);
        this.turnOrder = turnOrder;
    }

    public List<String> getTurnOrder(){ return this.turnOrder; }

    @Override
    protected boolean checkSpecificValidity() {
        return turnOrder != null && !turnOrder.contains(null);
    }
}
