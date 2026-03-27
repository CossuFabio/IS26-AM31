package it.polimi.ingsw.am31.am31.exceptions;

public class TooManyPlayersException extends Exception {
    public TooManyPlayersException() {
        super("Too many players");
    }
}
