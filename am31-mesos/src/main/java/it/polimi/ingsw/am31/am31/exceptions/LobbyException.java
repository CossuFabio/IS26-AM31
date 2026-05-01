package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

//Caused by illegal actions performed before the starting of the game
public abstract class LobbyException extends GameException {


    protected LobbyException(String message, ErrorCode code) {
        super(message, code);
        if(code.getCategory() != ErrorCategory.LOBBY_ERROR) throw new IllegalArgumentException("Wrong ErrorCategory!");
    }
}
