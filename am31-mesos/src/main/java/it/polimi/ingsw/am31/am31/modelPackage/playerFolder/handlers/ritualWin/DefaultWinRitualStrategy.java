package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Default ritual win strategy. Awards the standard prestige points bonus on ritual win. */
public class DefaultWinRitualStrategy implements IRitualWinStrategy{

    public DefaultWinRitualStrategy(){}

    @Override
    public void handleWinRitual(Player player, int bonus) {
        player.editPrestigePoints(bonus);
    }
}
