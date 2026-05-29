package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class IllegalSkipException extends IllegalActionException {
    public IllegalSkipException(BoardRows row) {
        super("Unable to skip draw from " + row + " line", ErrorCode.CANNOT_SKIP_DRAW);
    }
}
