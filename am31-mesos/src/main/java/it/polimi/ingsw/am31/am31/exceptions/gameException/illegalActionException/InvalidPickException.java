package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;

public abstract class InvalidPickException extends IllegalActionException {
    public InvalidPickException(String message) {
        super(message);
    }
}
