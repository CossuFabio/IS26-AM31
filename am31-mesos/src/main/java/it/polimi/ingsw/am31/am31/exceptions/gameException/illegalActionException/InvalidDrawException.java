package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class InvalidDrawException extends IllegalActionException {
    public InvalidDrawException() {
        super("Not enough drawing remaining!", ErrorCode.INVALID_DRAW);
    }
}
