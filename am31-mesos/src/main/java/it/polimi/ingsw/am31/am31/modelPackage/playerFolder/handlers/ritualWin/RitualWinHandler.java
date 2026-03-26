package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class RitualWinHandler{

    private IRitualWinStrategy handleWinStrategy;

    public RitualWinHandler(){
        this.handleWinStrategy = new DefaultWinRitualStrategy();
    }

    public void setStrategy(IRitualWinStrategy newStrategy){
        this.handleWinStrategy = newStrategy;
    }

    public void handleRitualWin(Player player, int bonus){
        handleWinStrategy.handleWinRitual(player, bonus);
    }

}

