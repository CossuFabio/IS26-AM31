package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to skip a draw that is not allowed to be skipped
 */
public class IllegalSkipException extends IllegalActionException {
    public IllegalSkipException(BoardRows row) {
        super("Unable to skip draw from " + row + " line", ErrorCode.CANNOT_SKIP_DRAW);
    }
}
