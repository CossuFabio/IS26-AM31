package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to join a lobby that has already reached its expected player count.
 */
public class TooManyPlayersException extends LobbyException {
    public TooManyPlayersException() {
        super("Too many players", ErrorCode.TOO_MANY_PLAYERS);
    }
}
