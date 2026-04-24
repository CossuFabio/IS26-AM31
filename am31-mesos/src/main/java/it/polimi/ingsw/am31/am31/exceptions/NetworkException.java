package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorCategory;

public abstract class NetworkException extends Exception {
    public NetworkException(String message) {
        super(message);
    }

    public ErrorCategory getCategory(){
        return ErrorCategory.NETWORK_ERROR;
    }

}
