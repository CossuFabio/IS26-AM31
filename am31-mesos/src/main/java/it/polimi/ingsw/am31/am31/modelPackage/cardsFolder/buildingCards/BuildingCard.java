package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards;


import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InsufficientFoodException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;

import java.util.function.Consumer;

/**
 * A building card that can be purchased during the action phase.
 * The food cost is reduced by the player's builder discount; the effect
 * is applied immediately on pick.
 */
public class BuildingCard extends Card implements IPickable {

    private final int cost;
    private final int prestigePointsGained;
    private final Consumer<Player> effect;
    private final String description;


    /**
     * @param cardId               unique identifier
     * @param era                  era this card belongs to
     * @param cost                 food cost to purchase
     * @param prestigePointsGained prestige points awarded at the end of the game
     * @param description          textual description of the effect
     * @param effect               behaviour applied to the player on pick, set at construction time
     */
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

    /**
     * Subtracts the discounted food cost and applies this card's effect to the player.
     *
     * @param player the player purchasing the card
     */
    public void onPick(Player player){

        //If player.getDiscount() is greater than this.cost then the players pays zero
        player.editFood(
                -Math.max(0, this.cost - player.getBuildersDiscount())
        );
        effect.accept(player);
    }

    @Override
    public void addToPlayer(Player player){
        player.addCard(this);
    }

    //Maybe remove the effect string and add an identifier field in the constructor
    @Override
    public String toString(){
        return "BuildingCard - Era: " + era + " - Prestige points: " + prestigePointsGained + " - Cost: " + cost ;
    }

    /**
     * Validates that the player can afford this card (food + builder discount >= cost).
     *
     * @param player the player attempting to pick
     * @throws InsufficientFoodException if the player cannot afford the card
     */
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


}
