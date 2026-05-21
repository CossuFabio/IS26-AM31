package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent.IPaintHandler;

public class EmptyPaintHandler implements IPaintHandler {

    public EmptyPaintHandler() {}

    @Override
    public void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus) {}
}
