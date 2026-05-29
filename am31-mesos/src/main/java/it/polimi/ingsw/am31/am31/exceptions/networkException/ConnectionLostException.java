package it.polimi.ingsw.am31.am31.exceptions.networkException;

import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class ConnectionLostException extends NetworkException {
    public ConnectionLostException() {
        super("Connection lost", ErrorCode.CONNECTION_LOST);
    }
}
