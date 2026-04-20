package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorCategory;

//Caused by in-game illegal actions
public abstract class IllegalActionException extends GameException {
    public IllegalActionException(String message) {
        super(message);
    }

    @Override
    public ErrorCategory getCategory(){return ErrorCategory.IN_GAME_ERROR; }

}
