package it.polimi.ingsw.am31.am31.exceptions;

public class IncorrectMethodCallException extends RuntimeException {
    public IncorrectMethodCallException(String methodName) {
        super("Incorrect usage of method : " + methodName);
    }
}
