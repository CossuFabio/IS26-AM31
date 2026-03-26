package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class ArtistDiscountBonus implements ISustainDiscountCharacter{

    public ArtistDiscountBonus(){}

    public int getBonus(Player player){

        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach((characterCard -> characterCard.acceptVisit(visitor)));
        return visitor.getArtists();

    }
}
