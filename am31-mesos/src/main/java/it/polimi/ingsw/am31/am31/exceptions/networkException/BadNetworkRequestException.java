package it.polimi.ingsw.am31.am31.exceptions.networkException;

import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class BadNetworkRequestException extends NetworkException {
    public BadNetworkRequestException(String type) {
        super("Missing parameters from " + type +" request", ErrorCode.BAD_REQUEST);
    }
}
