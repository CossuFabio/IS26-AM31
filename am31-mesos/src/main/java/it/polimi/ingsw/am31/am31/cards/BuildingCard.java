package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.BuildingStrategy.BuildingEffect;
import it.polimi.ingsw.am31.am31.Player;

public abstract class BuildingCard extends Card implements IPickable{
    private final int cost;
    private final int prestigePointsGained;
    private Player player;


    protected BuildingCard (int era, int cost, int prestigePointsGained){
        super(era);
        this.cost = cost;
        this.prestigePointsGained = prestigePointsGained;
    }


    public int getPrestigePointsGained(){
        return prestigePointsGained;
    }

    public int getCost() {
        return cost;
    }



}
