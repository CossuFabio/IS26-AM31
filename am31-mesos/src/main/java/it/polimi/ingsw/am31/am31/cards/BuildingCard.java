package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.BuildingStrategy.BuildingEffect;
import it.polimi.ingsw.am31.am31.Player;

public class BuildingCard extends Card {
    private int cost;
    private int prestigePointsGained;
    private Player player;
    private BuildingEffect effect;

    public void setEffect(BuildingEffect effect) {
        this.effect = effect;
    }

    public int getPrestigePointsGained(){
        return prestigePointsGained;
    }

    public int getCost() {
        return cost;
    }

    public void activateEffect() {
        effect.activateEffect(player);
    }

}
