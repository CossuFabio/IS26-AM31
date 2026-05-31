package it.polimi.ingsw.am31.am31.exceptions;

/**
 * Unchecked exception. Reports that something went wrong with the game flow handling.
 * Must not be sent to clients; handled server-side only.
 */
public abstract class GameInvariantException extends RuntimeException {
    protected GameInvariantException(String message) {
        super(message);
    }
}
