package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose.IRitualLoseStrategy;

public class EmptyRitualLoseStrategy implements IRitualLoseStrategy {

    public EmptyRitualLoseStrategy() {}

    @Override
    public void handleLose(Player player, int malus) {}
}
