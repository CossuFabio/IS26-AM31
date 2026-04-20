package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;

public class TooManyPlayersException extends LobbyException {
    public TooManyPlayersException() {
        super("Too many players");
    }
}
