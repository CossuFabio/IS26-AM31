package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Strategy that defines the player's behavior when losing the ritual. */
public interface IRitualLoseStrategy {

    /**
     * Resolves the ritual lose effect for the given player.
     *
     * @param player the player owning this handler
     * @param malus  prestige points malus applied
     */
    void handleLose(Player player, int malus);

}
