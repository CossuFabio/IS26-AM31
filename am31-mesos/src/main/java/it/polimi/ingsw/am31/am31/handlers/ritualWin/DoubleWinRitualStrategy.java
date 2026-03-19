package it.polimi.ingsw.am31.am31.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.Player;

public class DoubleWinRitualStrategy implements IRitualWinStrategy{

    public DoubleWinRitualStrategy(){}

    @Override
    public void handleWinRitual(Player player, int bonus) {
        player.editPrestigePoints(2*bonus);
    }

}
