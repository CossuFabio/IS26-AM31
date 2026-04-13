package it.polimi.ingsw.am31.am31.exceptions;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String nickname){super("Player " + nickname + " not found!"); }

}
