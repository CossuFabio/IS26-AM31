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
        //sconto base dei farmer
        int baseDiscount = player.getTribe().stream().mapToInt(CharacterCard::getSustainDiscount).sum();
        //sconti aggiuntivi da edifici
        int bonusDiscount = bonusDiscountEffects.stream().
                    mapToInt(bonus -> bonus.getBonus(player))
                    .sum();
        //calcolo sconto completo
        int fullDiscount = baseDiscount + bonusDiscount;
        //se cibo insufficiente, sottrae il malus e manda cibo a 0
        int sizeTribe = player.getTribe().size();
        if(food < (sizeTribe - fullDiscount)){
            int cannotPay = food - (sizeTribe - fullDiscount);
            player.editPrestigePoints(cannotPay*malus);
            player.editFood(-food);
        }
        //se cibo sufficiente, edita al nuovo valore.
        else
            player.editFood(-(sizeTribe - fullDiscount));
    }
}
