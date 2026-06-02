package it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

/**
 * Updates the client with the full list of card IDs in a board row (upper or lower).
 * The actual card objects are resolved client-side from the IDs
 */
public class CardLineUpdate extends UpdateMessage {

    private final List<String> cardIds;
    private final BoardRows row;

    @JsonCreator
    public CardLineUpdate(
            @JsonProperty("cardIds") List<String> cardIds,
            @JsonProperty("row") BoardRows row){
        super(UpdateMethodsConstants.BOARD_CARDLINE_UPDATE_METHOD);
        this.cardIds = cardIds;
        this.row = row;
    }

    public List<String> getCardIds(){return this.cardIds;}
    public BoardRows getRow(){return this.row; }

    @Override
    protected boolean checkSpecificValidity() {
        return cardIds != null && !cardIds.contains(null) && row != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
