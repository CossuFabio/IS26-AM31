package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;

public class PlayerAlreadyInGameException extends LobbyException {
    public PlayerAlreadyInGameException(String nickname) {
        super("Player " + nickname + " already in the game lobby");
    }
}
