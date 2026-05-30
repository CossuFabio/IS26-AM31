package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Strategy that defines the player's behavior when winning the ritual. */
public interface IRitualWinStrategy {
    /**
     * Resolves the ritual win effect for the given player.
     *
     * @param player the player owning this handler
     * @param bonus  prestige points bonus awarded
     */
    void handleWinRitual(Player player, int bonus);


}
