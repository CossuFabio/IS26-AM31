package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.cards.CharacterCard;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

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

        int baseDiscount = player.getTribe().stream().mapToInt(CharacterCard::getSustainDiscount).sum();
        int bonusDiscount = bonusDiscountEffects.stream().
                    mapToInt(bonus -> bonus.getBonus(player))
                    .sum();

        int fullDiscount = baseDiscount + bonusDiscount;

        int sizeTribe = player.getTribe().size();
        if(food < (sizeTribe - fullDiscount)){
            int cannotPay = food - (sizeTribe - fullDiscount);
            player.editPrestigePoints(cannotPay*malus);
            player.editFood(-food);
        }
        else
            player.editFood(-(sizeTribe - fullDiscount));
    }
}
