package it.polimi.ingsw.am31.am31.exceptions;

public class PlayerAlreadyInGameException extends RuntimeException {
    public PlayerAlreadyInGameException(String message) {
        super("Player" + message + "already in the game lobby");
    }
}
