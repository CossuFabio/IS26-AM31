package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

public class InsufficientPlayersNumberException extends GameInvariantException {
    public InsufficientPlayersNumberException() {
        super("Insufficient players");
    }
}
