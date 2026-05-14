package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards;


import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InsufficientFoodException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;

import java.util.function.Consumer;

public class BuildingCard extends Card implements IPickable {

    private final int cost;
    private final int prestigePointsGained;
    private final Consumer<Player> effect;
    private final String description;


    public BuildingCard (String cardId, int era, int cost, int prestigePointsGained,
            String description, Consumer<Player> effect){
        super(cardId, era);
        this.cost = cost;
        this.prestigePointsGained = prestigePointsGained;
        this.effect = effect;
        this.description = description;
    }


    public int getPrestigePointsGained(){
        return prestigePointsGained;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription(){return description;}

    public void onPick(Player player){

        //If player.getDiscount() is greater than this.cost then the players pays zero
        player.editFood(
                -Math.max(0, this.cost - player.getBuildersDiscount())
        );
        effect.accept(player);
    }

    public void addToPlayer(Player player){
        player.addCard(this);
    }

    //Maybe remove the effect string and add an identifier field in the constructor
    @Override
    public String toString(){
        return "BuildingCard - Era: " + era + " - Prestige points: " + prestigePointsGained + " - Cost: " + cost;
    }

    @Override
    public void canPick(Player player) throws InsufficientFoodException {
        if(! (player.getFood() + player.getBuildersDiscount() >= this.cost)) throw new InsufficientFoodException(player.getFood(), player.getBuildersDiscount(), this.cost);
    }

    @Override
    public void acceptVisit(TribeVisitor visitor){
        visitor.visit(this);
    }


    @Override
    public boolean canBePicked() {
        return true;
    }

    @Override
    public boolean canAffordWithFood(int food, int discount) {
        return food + discount >= this.cost;
    }

    @Override
    public  boolean isCharacter() {return false;}
}
