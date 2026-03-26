package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class DoubleWinRitualStrategy implements IRitualWinStrategy{

    public DoubleWinRitualStrategy(){}

    @Override
    public void handleWinRitual(Player player, int bonus) {

        player.editPrestigePoints(2*bonus);
    }
}
