package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

public class PlayerColorAlreadyTakenException extends RuntimeException {
    public PlayerColorAlreadyTakenException(Color color) {
        super("Color "+ " alraedy taken by another player");
    }
}
