package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;

import java.util.ArrayList;

public class DefaultSustainHandler{


    private final ArrayList<ISustainDiscountCharacter> bonusDiscountEffects ;


    public DefaultSustainHandler(){
        bonusDiscountEffects = new ArrayList<ISustainDiscountCharacter>();
    }

    public void addSustainDiscountEffect(ISustainDiscountCharacter newEffect){
        bonusDiscountEffects.add(newEffect);
    }


    public void handleSustain(Player player, int malus) {
        int food = player.getFood();

        //Base discount given by farmers
        int baseDiscount = player.getTribe().stream().mapToInt(CharacterCard::getSustainDiscount).sum();
        //Discount given by bonus effects
        int bonusDiscount = bonusDiscountEffects.stream().
                    mapToInt(bonus -> bonus.getBonus(player))
                    .sum();

        //Compute full discount
        int fullDiscount = baseDiscount + bonusDiscount;

        //If insufficient, food is set to zero and player loses prestige points
        int sizeTribe = player.getTribe().size();

        if(food < (sizeTribe - fullDiscount)){
            int cannotPay = food - (sizeTribe - fullDiscount); //Negative value
            player.editPrestigePoints(cannotPay*Math.abs(malus)); //Put malus as positive
            player.editFood(-food);
        } else {
            if(fullDiscount > sizeTribe)
                fullDiscount = sizeTribe;
            player.editFood(-(sizeTribe - fullDiscount));
        }
    }
}
