package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;

public class UsernameAlreadyTakenException extends LobbyException {
    public UsernameAlreadyTakenException() {
        super("Username already taken!");
    }
}
