package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public interface IPaintHandler {
    void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus);
}
