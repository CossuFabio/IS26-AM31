package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Thrown when a player tries to join a game, but they are already in another one
 */
public class PlayerAlreadyInGameException extends LobbyException {
    public PlayerAlreadyInGameException(String nickname) {
        super("Player " + nickname + " already in the game lobby", ErrorCode.PLAYER_ALREADY_IN_GAME);
    }
}
