package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class NoMalusRitualLoseStrategy implements IRitualLoseStrategy{

    public NoMalusRitualLoseStrategy(){}

    @Override
    public void handleLose(Player player, int malus) {
        //Do nothing
    }
}
