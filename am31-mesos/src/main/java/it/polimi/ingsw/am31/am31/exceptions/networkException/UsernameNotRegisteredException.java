package it.polimi.ingsw.am31.am31.exceptions.networkException;

import it.polimi.ingsw.am31.am31.exceptions.NetworkException;

public class UsernameNotRegisteredException extends NetworkException {
    public UsernameNotRegisteredException() {
        super("You must be registered to server before sending requests!");
    }
}
