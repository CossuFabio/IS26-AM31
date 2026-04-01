package it.polimi.ingsw.am31.am31.exceptions;

public class WrongPlayerTurnException extends Exception {
    public WrongPlayerTurnException() {
        super("Wrong turn!");
    }
}
