package it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

/**
 * Notifies the client with the ID of the event card that has just been resolved.
 * The actual card object is resolved client-side from the ID
 */
public class GameEventResolveUpdate extends UpdateMessage {
    private final String cardId;

    @JsonCreator
    public GameEventResolveUpdate (@JsonProperty("cardId") String cardId){
        super(UpdateMethodsConstants.GAME_EVENT_RESOLVE_UPDATE);
        this.cardId = cardId;
    }

    public String getCardId() {return cardId;}

    @Override
    protected boolean checkSpecificValidity() {
        return cardId != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor) {
        visitor.handleUpdateMessage(this);
    }
}
