package it.polimi.ingsw.am31.am31.network.errorMessage;

import it.polimi.ingsw.am31.am31.exceptions.GameException;
import it.polimi.ingsw.am31.am31.exceptions.NetworkException;

public class ErrorMessageFactory {

    public static ErrorMessage createErrorMessage(GameException e){
        return new ErrorMessage(
                e.getMessage(),
                e.getCategory()
        );
    }

    public static ErrorMessage createErrorMessage(NetworkException e){
        return new ErrorMessage(
                e.getMessage(),
                e.getCategory()
        );
    }


}
