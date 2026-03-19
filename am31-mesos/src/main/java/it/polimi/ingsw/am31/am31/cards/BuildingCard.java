package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.BuildingStrategy.BuildingEffect;
import it.polimi.ingsw.am31.am31.Player;

public class BuildingCard extends Card {
    private final int cost;
    private final int prestigePointsGained;
    private Player player;
    private BuildingEffect effect;

    public BuildingCard (BuildingEffect effect, int cost, int prestigePointsGained){
        this.cost = cost;
        this.prestigePointsGained = prestigePointsGained;
        this.effect = effect;

    }

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

    public BuildingEffect getEffect() {
        return effect;
    }
}
