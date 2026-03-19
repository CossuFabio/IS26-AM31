package it.polimi.ingsw.am31.am31.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.Player;

public class DefaultWinRitualStrategy implements IRitualWinStrategy{

    public DefaultWinRitualStrategy(){}

    @Override
    public void handleWinRitual(Player player, int bonus) {
        player.editPrestigePoints(bonus);
    }
}
