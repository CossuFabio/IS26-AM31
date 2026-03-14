package it.polimi.ingsw.am31.am31.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.Player;

public interface IPaintHandler {
    void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus);
}
