package it.polimi.ingsw.am31.am31.exceptions.networkException;

import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Exception thrown when a client tries to register with the server using a username already taken by another client.
 */
public class UsernameAlreadyInUseException extends NetworkException {
    public UsernameAlreadyInUseException(String username) {
        super("Username " + username + " already in use!", ErrorCode.USERNAME_ALREADY_IN_USE);
    }
}
