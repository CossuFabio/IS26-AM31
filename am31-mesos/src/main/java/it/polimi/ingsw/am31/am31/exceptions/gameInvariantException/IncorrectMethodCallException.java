package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

/**
 * Thrown when a method is called at the wrong point in the game flow.
 */
public class IncorrectMethodCallException extends GameInvariantException {
    public IncorrectMethodCallException(String methodName) {
        super("Incorrect usage of method : " + methodName);
    }

    public IncorrectMethodCallException(String methodName, String reason){super("Incorrect usage of method: " + methodName + ". Reason: " + reason);}

}
