package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to put their totem on a non-existing offer card
 */
public class OfferCardNotFoundException extends IllegalActionException {
    public OfferCardNotFoundException() {
        super("Offer card not found!", ErrorCode.OFFER_CARD_NOT_FOUND);
    }
}
