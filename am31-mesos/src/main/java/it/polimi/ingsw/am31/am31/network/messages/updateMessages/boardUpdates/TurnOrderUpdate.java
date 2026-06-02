package it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.PlayerMessage;

import java.util.List;

/**
 * Updates the client with the current turn order as an ordered list of players.
 * WARNING: the list may contain null values for empty tiles on the turn order card.
 */
public class TurnOrderUpdate extends UpdateMessage {

    private final List<PlayerMessage> turnOrder;

    @JsonCreator
    public TurnOrderUpdate(@JsonProperty("turnOrder") List<PlayerMessage> turnOrder){
        super(UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD);
        this.turnOrder = turnOrder;
    }

    public List<PlayerMessage> getTurnOrder(){ return this.turnOrder; }

    @Override
    protected boolean checkSpecificValidity() {
        return turnOrder != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
