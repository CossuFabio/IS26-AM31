package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

public abstract class InvalidPickException extends IllegalActionException {
    protected InvalidPickException(String message, ErrorCode code) {
        super(message, code);
    }
}
