package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

/** Provides a sustain food discount based on the number of farmers in the tribe. */
public class FarmerDiscountBonus implements ISustainDiscountCharacter{

    public static final int DISCOUNT_GIVEN = 1;
    public FarmerDiscountBonus(){}

    public int getBonus(Player player){
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach((characterCard -> characterCard.acceptVisit(visitor)));
        return DISCOUNT_GIVEN * visitor.getFarmers();
    }

}
