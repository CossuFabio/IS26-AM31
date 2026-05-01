package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

public class GameAlreadyStartedException extends LobbyException {
    public GameAlreadyStartedException() {
        super("Game already started!", ErrorCode.GAME_ALREADY_STARTED);
    }
}
