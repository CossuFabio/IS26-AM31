package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;

public class OfferTrackTileAlreadyTakenException extends IllegalActionException {
    public OfferTrackTileAlreadyTakenException() {
        super("Offer track already taken!");
    }
}
