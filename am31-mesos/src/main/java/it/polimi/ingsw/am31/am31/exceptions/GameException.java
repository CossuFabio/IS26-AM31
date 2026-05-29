package it.polimi.ingsw.am31.am31.exceptions;


import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

//Generic type used by catcher. GameException are caused by wrong request, to they must be sent to the requesting client
public abstract class GameException extends Exception {

    private final ErrorCode errorCode;

    protected GameException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    //Client should know which category the exception is from

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ErrorCategory getErrorCategory(){
        return errorCode.getCategory();
    }

}
