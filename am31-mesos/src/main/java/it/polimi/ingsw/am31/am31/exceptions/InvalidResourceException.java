package it.polimi.ingsw.am31.am31.exceptions;

public class InvalidResourceException extends RuntimeException {
    public InvalidResourceException(String resourceTypeString) {
        super(resourceTypeString + " request invalid");
    }
}
