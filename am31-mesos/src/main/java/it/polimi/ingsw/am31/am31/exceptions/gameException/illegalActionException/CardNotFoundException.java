package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

public class CardNotFoundException extends IllegalActionException {
    public CardNotFoundException() {
        super("Card not found!", ErrorCode.CARD_NOT_FOUND);
    }
}
