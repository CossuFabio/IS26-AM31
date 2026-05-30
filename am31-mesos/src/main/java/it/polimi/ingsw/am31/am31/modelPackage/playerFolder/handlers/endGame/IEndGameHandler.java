package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Handler for end-game player actions. */
public interface IEndGameHandler {
    /**
     * Resolves end-game effects for the given player.
     *
     * @param player the player owning this handler
     */
    void handleEndGame(Player player);
}
