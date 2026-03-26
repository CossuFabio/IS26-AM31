package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public interface IHuntHandler {
    void handleHunt(Player player, int food, int prestigePoints);
}
