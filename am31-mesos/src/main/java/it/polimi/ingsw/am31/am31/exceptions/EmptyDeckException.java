package it.polimi.ingsw.am31.am31.exceptions;

public class EmptyDeckException extends Exception {
    public EmptyDeckException() {
        super("Deck is empty!");
    }
}
