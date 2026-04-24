package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;

public class GameAlreadyStartedException extends LobbyException {
    public GameAlreadyStartedException() {
        super("Game already started!");
    }
}
