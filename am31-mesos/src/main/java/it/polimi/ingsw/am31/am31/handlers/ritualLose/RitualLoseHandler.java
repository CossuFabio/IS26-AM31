package it.polimi.ingsw.am31.am31.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.Player;

public class RitualLoseHandler {

    private IRitualLoseStrategy handleLoseStrategy;

    public RitualLoseHandler(){
        this.handleLoseStrategy = new DefaultRitualLoseStrategy();
    }

    public void setHandleLoseStrategy(IRitualLoseStrategy handleLoseStrategy) {
        this.handleLoseStrategy = handleLoseStrategy;
    }

    public void handleLose(Player player, int malus){
        handleLoseStrategy.handleLose(player, malus);
    }
}
