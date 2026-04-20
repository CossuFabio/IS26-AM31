package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;

public class PlayerAlreadyInGameException extends LobbyException {
    public PlayerAlreadyInGameException(String message) {
        super("Player" + message + "already in the game lobby");
    }
}
