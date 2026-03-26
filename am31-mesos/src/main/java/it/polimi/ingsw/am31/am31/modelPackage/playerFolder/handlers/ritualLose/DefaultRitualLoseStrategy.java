package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class DefaultRitualLoseStrategy implements IRitualLoseStrategy{

    public DefaultRitualLoseStrategy(){}

    @Override
    public void handleLose(Player player, int malus) {
        player.editPrestigePoints(-1* malus);
    }
}
