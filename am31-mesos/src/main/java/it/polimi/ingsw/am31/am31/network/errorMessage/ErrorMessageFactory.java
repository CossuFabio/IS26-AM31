package it.polimi.ingsw.am31.am31.network.errorMessage;

import it.polimi.ingsw.am31.am31.exceptions.GameException;

public class ErrorMessageFactory {

    public static ErrorMessage createErrorMesssage(GameException e){
        return new ErrorMessage(
                e.getMessage(),
                e.getCategory()
        );
    }

}
