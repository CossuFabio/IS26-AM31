package it.polimi.ingsw.am31.am31.exceptions;


import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorCategory;

//Generic type used by catcher. GameException are caused by wrong request, to they must be sent to the requesting client
public abstract class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }

    //Client should know which category the exception is from
    public abstract ErrorCategory getCategory();

}
