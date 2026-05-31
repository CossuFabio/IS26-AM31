package it.polimi.ingsw.am31.am31.exceptions;


import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;


/**
 * Base class for game-related exceptions that must be reported back to the requesting client.
 */
public abstract class GameException extends Exception {

    private final ErrorCode errorCode;

    protected GameException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    //Client should know which category the exception is from

    /**
     * @return the associated error code
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * @return the associated error category
     */
    public ErrorCategory getErrorCategory(){
        return errorCode.getCategory();
    }

}
