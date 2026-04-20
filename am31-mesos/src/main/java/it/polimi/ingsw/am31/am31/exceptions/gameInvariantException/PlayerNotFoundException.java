package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

public class PlayerNotFoundException extends GameInvariantException {

    public PlayerNotFoundException(String nickname){super("Player " + nickname + " not found!"); }

}
