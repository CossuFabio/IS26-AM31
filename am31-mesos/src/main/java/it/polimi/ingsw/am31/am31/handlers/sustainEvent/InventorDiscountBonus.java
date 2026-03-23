package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

public class InventorDiscountBonus implements ISustainDiscountCharacter{
    public InventorDiscountBonus(){}
    @Override
    public int getBonus(Player player){
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach((characterCard -> characterCard.acceptVisit(visitor)));
        return visitor.getInventors();
    }
}
