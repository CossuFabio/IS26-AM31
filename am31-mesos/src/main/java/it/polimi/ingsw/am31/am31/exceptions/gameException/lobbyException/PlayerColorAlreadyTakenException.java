package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

public class PlayerColorAlreadyTakenException extends LobbyException {
    public PlayerColorAlreadyTakenException(Color color) {
        super("Color "+ color +  " alraedy taken by another player");
    }
}
