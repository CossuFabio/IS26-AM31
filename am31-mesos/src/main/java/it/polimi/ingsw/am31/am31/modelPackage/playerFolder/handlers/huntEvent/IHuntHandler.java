package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Handler for the hunt event resolution. */
public interface IHuntHandler {
    /**
     * Resolves the hunt event for the given player.
     *
     * @param player         the player owning this handler
     * @param food           food reward per hunter
     * @param prestigePoints prestige points reward per hunter
     */
    void handleHunt(Player player, int food, int prestigePoints);
}
