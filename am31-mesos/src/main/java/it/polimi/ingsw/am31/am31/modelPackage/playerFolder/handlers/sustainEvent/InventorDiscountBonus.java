package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class InventorDiscountBonus implements ISustainDiscountCharacter{

    private static int DISCOUNT_GIVEN = 1;

    public InventorDiscountBonus(){}
    @Override
    public int getBonus(Player player){
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach((characterCard -> characterCard.acceptVisit(visitor)));
        return DISCOUNT_GIVEN * visitor.getInventors();
    }
}
