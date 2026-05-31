package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

/**
 * Exception thrown when a method tries to modify the state of the game when it is no longer active.
 * Thrown after the game ends or after a player disconnects.
 */
public class GameNoLongerActiveException extends GameInvariantException {
    public GameNoLongerActiveException() {
        super("Game no longer active!");
    }
}
