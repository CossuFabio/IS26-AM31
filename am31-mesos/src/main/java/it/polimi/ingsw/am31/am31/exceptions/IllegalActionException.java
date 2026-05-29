package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

//Caused by in-game illegal actions
public abstract class IllegalActionException extends GameException {

    protected IllegalActionException(String message, ErrorCode errorCode) {
        super(message, errorCode);
        if(errorCode.getCategory() != ErrorCategory.IN_GAME_ERROR) throw new IllegalArgumentException("Wrong ErrorCategory!");
    }



}
