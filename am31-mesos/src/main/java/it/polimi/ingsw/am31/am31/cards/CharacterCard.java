package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class CharacterCard extends Card {

    public CharacterCard(int era) {
        super(era);
    }
    public void onPick() {

    }
    public int getBuildingDiscount () {
    return 0;
    }
    public int getPrestigePoints () {
        return 0;
    }
    public IconEnum getIcon () {
        return IconEnum.EMPTY;
    }
    public int getSustainDiscount (){
        return 0;
    }
    public boolean getMark() { return false; }
    public int getStars() { return 0; }
    public int acceptVisit (TribeVisitor) {
        return 0;
    }
}
