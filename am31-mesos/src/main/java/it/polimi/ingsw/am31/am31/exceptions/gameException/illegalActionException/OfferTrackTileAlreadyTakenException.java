package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to put their totem on an offer card already occupied by another player.
 */
public class OfferTrackTileAlreadyTakenException extends IllegalActionException {
    public OfferTrackTileAlreadyTakenException() {
        super("Offer track already taken!", ErrorCode.OFFER_TRACK_TILE_ALREADY_TAKEN);
    }
}
