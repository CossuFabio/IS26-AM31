package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/**
 * Exception thrown when a method tries to put the totem of a player in the turn order tile, but it is already present
 */
public class PlayerAlreadyInTurnOrderException extends RuntimeException {
    public PlayerAlreadyInTurnOrderException(Player p) {
        super("Player " + p.getNickname() + " already placed his totem in the TurnOrder!");
    }
}
