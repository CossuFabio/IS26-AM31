package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCategory;

//Caused by illegal actions performed before the starting of the game
public abstract class LobbyException extends GameException {
    public LobbyException(String message) {
        super(message);
    }

    @Override
    public ErrorCategory getCategory(){return ErrorCategory.LOBBY_ERROR; }

}
