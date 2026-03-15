package it.polimi.ingsw.am31.am31.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.Player;

public interface IRitualLoseStrategy {

    void handleLose(Player player, int malus);

}
