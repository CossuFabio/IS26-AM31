package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards;


import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;

import java.util.function.Consumer;

public class BuildingCard extends Card implements IPickable {
    private final int cost;
    private final int prestigePointsGained;
    private Player player;
    private final Consumer<Player> effect;


    //For generics: check the correct handler then create like this:
    //              new Building(era, cost, pp, (player) -> {player.addWinRitualEffect(DoubleWinRitualStrategy::new)});
    //For plain +25 PP building:
    //  new Building(era, cost, pp, (player) -> player.editPP(25));


    public BuildingCard (int era, int cost, int prestigePointsGained, Consumer<Player> effect){
        super(era);
        this.cost = cost;
        this.prestigePointsGained = prestigePointsGained;
        this.effect = effect;
    }


    public int getPrestigePointsGained(){
        return prestigePointsGained;
    }

    public int getCost() {
        return cost;
    }

    public void onPick(Player player){
        effect.accept(player);
    }

    public void addToPlayer(Player player){
        player.addCard(this);
    }

    //Maybe remove the effect string and add an identifier field in the constructor
    @Override
    public String toString(){
        return "BuildingCard - Era: " + era + " - Prestige points: " + prestigePointsGained + " - Cost: " + cost
                + " - Effect: " + effect;
    }

    @Override
    public boolean canPick(Player player){
        return (player.getFood() + player.getBuildersDiscount() < this.cost);
    }

}
