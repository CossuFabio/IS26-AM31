package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

/**
 * Thrown when attempting to start the game without the minimum required number of players.
 */
public class InsufficientPlayersNumberException extends GameInvariantException {
    public InsufficientPlayersNumberException() {
        super("Insufficient players");
    }
}
