package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class TooManyPlayersException extends LobbyException {
    public TooManyPlayersException() {
        super("Too many players", ErrorCode.TOO_MANY_PLAYERS);
    }
}
