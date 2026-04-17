package it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class CardLineUpdate extends UpdateMessage {

    private final List<String> cardIds;
    private final BoardRows row;

    public CardLineUpdate(List<String> cardIds, BoardRows row){
        super(UpdateMethodsConstants.BOARD_CARDLINE_UPDATE_METHOD);
        this.cardIds = cardIds;
        this.row = row;
    }

    public List<String> getCardIds(){return this.cardIds;}
    public BoardRows getRow(){return this.row; }
}
