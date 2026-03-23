package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Shaman extends CharacterCard {
    private int stars;

    public Shaman(int era, int stars) {
        super(era);
        this.stars = stars;
    }
//for JSON use
    public Shaman() {}
    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public int getStars() {return stars;}

    @Override
    public void onPick(Player player){
        player.increaseStars(this.stars);
    }
    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }
}