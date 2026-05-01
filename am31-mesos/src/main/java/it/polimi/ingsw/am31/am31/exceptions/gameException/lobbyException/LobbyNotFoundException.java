package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

public class LobbyNotFoundException extends LobbyException {
    public LobbyNotFoundException (int gameId){
        super("Lobby " + gameId + " not found", ErrorCode.LOBBY_NOT_FOUND);
    }
}
