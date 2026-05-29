package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class InvalidResourceException extends IllegalActionException {
    public InvalidResourceException(InvalidResourceTypeEnum resourceType) {
        super(resourceType+ " request invalid", ErrorCode.INVALID_RESOURCE);
    }
}
