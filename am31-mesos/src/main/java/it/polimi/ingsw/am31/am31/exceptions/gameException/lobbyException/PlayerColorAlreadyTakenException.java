package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class PlayerColorAlreadyTakenException extends LobbyException {
    public PlayerColorAlreadyTakenException(Color color) {
        super("Color "+ color +  " already taken by another player", ErrorCode.PLAYER_COLOR_ALREADY_TAKEN);
    }
}
