package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;

public class InvalidResourceException extends IllegalActionException {
    public InvalidResourceException(String resourceTypeString) {
        super(resourceTypeString + " request invalid");
    }
}
