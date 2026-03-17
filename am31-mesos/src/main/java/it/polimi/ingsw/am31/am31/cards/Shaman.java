package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.ShamanVisitor;

public class Shaman extends CharacterCard {
    private final int stars;

    public Shaman(int era, int stars) {
        super(era);
        this.stars = stars;
    }

    @Override
    public int getStars() {return stars;}

    @Override
    public void onPick(Player player){
        //player.increaseStars(this.stars);
    }
//    @Override
//    public int acceptVisit(ShamanVisitor ShamanVisitor) {
//        return 1;
//    }
}
