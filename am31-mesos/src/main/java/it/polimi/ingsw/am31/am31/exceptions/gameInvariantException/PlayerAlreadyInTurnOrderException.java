package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class PlayerAlreadyInTurnOrderException extends RuntimeException {
    public PlayerAlreadyInTurnOrderException(Player p) {
        super("Player " + p.getNickname() + " alread placed is totem in the TurnOrder!");
    }
}
