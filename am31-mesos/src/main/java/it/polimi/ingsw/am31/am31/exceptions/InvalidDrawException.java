package it.polimi.ingsw.am31.am31.exceptions;

public class InvalidDrawException extends Exception {
    public InvalidDrawException() {
        super("Not enough drawing remaining! ");
    }
}
