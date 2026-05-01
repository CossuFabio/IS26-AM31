package it.polimi.ingsw.am31.am31.exceptions.networkException;

import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCode;

public class UsernameAlreadyInUseException extends NetworkException {
    public UsernameAlreadyInUseException(String username) {
        super("Username " + username + " already in use!", ErrorCode.USERNAME_ALREADY_IN_USE);
    }
}
