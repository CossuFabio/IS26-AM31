package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

public class UsernameAlreadyTakenException extends LobbyException {
    public UsernameAlreadyTakenException() {
        super("Username already taken!", ErrorCode.USERNAME_ALREADY_TAKEN);
    }
}
