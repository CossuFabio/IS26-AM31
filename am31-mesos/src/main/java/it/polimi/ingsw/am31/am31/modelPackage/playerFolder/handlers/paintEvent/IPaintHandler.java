package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Handler for the painting event resolution. */
public interface IPaintHandler {
    
    /**
     * Resolves the painting event for the given player.
     *
     * @param player                the player owning this handler
     * @param minimumArtistsNumber  minimum number of artists required to receive the bonus
     * @param prestigePointsBonus   prestige points awarded per artist if the minimum is met
     * @param prestigePointsMalus   prestige points lost if the minimum is not met
     */
    void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus);
}
