package it.polimi.ingsw.am31.am31.exceptions;

public class UsernameAlreadyTakenException extends Exception {
    public UsernameAlreadyTakenException() {
        super("Username already taken!");
    }
}
