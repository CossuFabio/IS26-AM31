package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to draw from a card line when they are not allowed to
 */
public class InvalidDrawException extends IllegalActionException {
    public InvalidDrawException() {
        super("Not enough drawing remaining!", ErrorCode.INVALID_DRAW);
    }
    public InvalidDrawException(String message) {
        super(message, ErrorCode.INVALID_DRAW);
    }
}
