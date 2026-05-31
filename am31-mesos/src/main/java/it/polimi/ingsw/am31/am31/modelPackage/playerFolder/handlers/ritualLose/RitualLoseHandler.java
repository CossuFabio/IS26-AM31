package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Base handler for the ritual lose event. */
public class RitualLoseHandler {

    private IRitualLoseStrategy handleLoseStrategy;

    public RitualLoseHandler(){
        this.handleLoseStrategy = new DefaultRitualLoseStrategy();
    }

    /** Changes the behaviour of the player on losing the ritual event. */
    public void setStrategy(IRitualLoseStrategy handleLoseStrategy) {
        this.handleLoseStrategy = handleLoseStrategy;
    }

    /**
     * Resolves the ritual lose effect for the given player using the current strategy.
     * @param player the player owning this handler
     * @param malus prestige points malus to apply
     */
    public void handleLose(Player player, int malus){
        handleLoseStrategy.handleLose(player, malus);
    }
}
