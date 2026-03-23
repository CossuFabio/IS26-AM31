package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

public class ArtistDiscountBonus implements ISustainDiscountCharacter{

    public ArtistDiscountBonus(){}

    public int getBonus(Player player){

        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach((characterCard -> characterCard.acceptVisit(visitor)));
        return visitor.getArtists();

    }
}
