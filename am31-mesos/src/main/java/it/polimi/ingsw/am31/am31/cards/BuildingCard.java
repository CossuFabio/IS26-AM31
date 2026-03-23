package it.polimi.ingsw.am31.am31.cards;


import it.polimi.ingsw.am31.am31.Player;

import java.util.function.Consumer;

public class BuildingCard extends Card implements IPickable{
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

}
