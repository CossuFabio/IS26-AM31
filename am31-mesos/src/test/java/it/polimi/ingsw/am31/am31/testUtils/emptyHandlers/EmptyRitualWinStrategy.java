package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin.IRitualWinStrategy;

public class EmptyRitualWinStrategy implements IRitualWinStrategy {

    public EmptyRitualWinStrategy() {}

    @Override
    public void handleWinRitual(Player player, int bonus) {}
}
