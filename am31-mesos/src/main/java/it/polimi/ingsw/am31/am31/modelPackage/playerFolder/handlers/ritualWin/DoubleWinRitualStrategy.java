package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Ritual win strategy that doubles the prestige points bonus on ritual win. */
public class DoubleWinRitualStrategy implements IRitualWinStrategy{

    private static final int MULTIPLIER = 2;

    public DoubleWinRitualStrategy(){}

    @Override
    public void handleWinRitual(Player player, int bonus) {

        player.editPrestigePoints(MULTIPLIER*bonus);
    }
}
