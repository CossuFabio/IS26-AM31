package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

/**
 * Thrown when the resource finder cannot find a player by name even though they are part of the game.
 */
public class PlayerNotFoundException extends GameInvariantException {

    public PlayerNotFoundException(String nickname){super("Player " + nickname + " not found!"); }

}
