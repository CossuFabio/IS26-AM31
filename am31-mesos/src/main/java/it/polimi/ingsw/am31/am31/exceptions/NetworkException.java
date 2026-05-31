package it.polimi.ingsw.am31.am31.exceptions;

import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

/**
 * Errors related to the network layer.
 * If the connection is still available, the error is sent to the client.
 */
public abstract class NetworkException extends Exception {

    private final ErrorCode errorCode;
    protected NetworkException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        if(errorCode.getCategory() != ErrorCategory.NETWORK_ERROR) throw new IllegalArgumentException("Wrong category!");
    }

    /**
     * @return the associated error category
     */
    public ErrorCategory getErrorCategory(){
        return errorCode.getCategory();
    }

    /**
     * @return the associated error code
     */
    public ErrorCode getErrorCode(){
        return errorCode;
    }



}
