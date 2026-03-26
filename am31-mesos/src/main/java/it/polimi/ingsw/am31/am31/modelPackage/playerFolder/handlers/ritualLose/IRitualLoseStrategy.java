package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public interface IRitualLoseStrategy {

    void handleLose(Player player, int malus);

}
