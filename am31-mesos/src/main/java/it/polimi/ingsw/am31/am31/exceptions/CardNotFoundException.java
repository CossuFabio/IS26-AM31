package it.polimi.ingsw.am31.am31.exceptions;

public class CardNotFoundException extends Exception {
    public CardNotFoundException() {
        super("Card not found!");
    }
}
