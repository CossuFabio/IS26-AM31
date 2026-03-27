package it.polimi.ingsw.am31.am31.exceptions;

public class InsufficientPlayersNumberException extends Exception {
    public InsufficientPlayersNumberException() {
        super("Insufficient players");
    }
}
