package it.polimi.ingsw.am31.am31.exceptions;

public abstract class GameInvariantException extends RuntimeException {
    protected GameInvariantException(String message) {
        super(message);
    }
}
