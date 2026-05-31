package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a client tries to create a game with a player count outside the allowed range.
 */
public class InvalidPlayersNumberException extends LobbyException {
    public InvalidPlayersNumberException() {
        super("Player number must be between " + GameConstants.MIN_PLAYERS + " and " + GameConstants.MAX_PLAYERS, ErrorCode.INVALID_PLAYER_NUMBER);
    }
}
