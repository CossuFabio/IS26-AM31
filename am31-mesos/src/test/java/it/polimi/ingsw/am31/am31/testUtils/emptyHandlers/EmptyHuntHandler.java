package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.IHuntHandler;

public class EmptyHuntHandler implements IHuntHandler {

    public EmptyHuntHandler() {}

    @Override
    public void handleHunt(Player player, int food, int prestigePoints) {}
}
