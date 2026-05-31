package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

/**
 * Thrown when a method tries to put a player on the offer track but all players have already played their turn.
 */
public class EverybodyPlayedException extends GameInvariantException {
    public EverybodyPlayedException() {
        super("No more players");
    }
}
