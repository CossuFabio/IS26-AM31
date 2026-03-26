package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class RitualLoseHandler {

    private IRitualLoseStrategy handleLoseStrategy;

    public RitualLoseHandler(){
        this.handleLoseStrategy = new DefaultRitualLoseStrategy();
    }

    public void setStrategy(IRitualLoseStrategy handleLoseStrategy) {
        this.handleLoseStrategy = handleLoseStrategy;
    }

    public void handleLose(Player player, int malus){
        handleLoseStrategy.handleLose(player, malus);
    }
}
