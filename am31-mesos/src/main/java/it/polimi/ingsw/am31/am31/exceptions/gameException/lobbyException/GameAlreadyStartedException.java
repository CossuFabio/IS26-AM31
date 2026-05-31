package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to enter the lobby after the game has already started.
 */
public class GameAlreadyStartedException extends LobbyException {
    public GameAlreadyStartedException() {
        super("Game already started!", ErrorCode.GAME_ALREADY_STARTED);
    }
}
