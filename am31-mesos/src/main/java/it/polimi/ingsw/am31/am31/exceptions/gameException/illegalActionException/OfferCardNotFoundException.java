package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;

public class OfferCardNotFoundException extends IllegalActionException {
    public OfferCardNotFoundException() {
        super("Offer card not found!");
    }
}
