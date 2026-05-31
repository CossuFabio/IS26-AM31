package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Base handler for the ritual win event. */
public class RitualWinHandler{

    private IRitualWinStrategy handleWinStrategy;

    public RitualWinHandler(){
        this.handleWinStrategy = new DefaultWinRitualStrategy();
    }

    /** Changes the behaviour of the player on winning the ritual event. */
    public void setStrategy(IRitualWinStrategy newStrategy){
        this.handleWinStrategy = newStrategy;
    }

    /**
     * Resolves the ritual win effect for the given player using the current strategy.
     * @param player the player owning this handler
     * @param bonus prestige points bonus to award
     */
    public void handleRitualWin(Player player, int bonus){
        handleWinStrategy.handleWinRitual(player, bonus);
    }

}

