package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to make a game move when it is not their turn.
 */
public class WrongPlayerTurnException extends IllegalActionException {
    public WrongPlayerTurnException() {
        super("Wrong turn!", ErrorCode.WRONG_PLAYER_TURN);
    }
}
