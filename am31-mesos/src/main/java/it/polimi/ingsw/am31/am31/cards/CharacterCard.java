package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public abstract class CharacterCard extends Card implements IPickable {

    protected CharacterCard(int era) {
        super(era);
    }
    @Override
    public void onPick(Player player) {}

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
    public void acceptVisit (TribeVisitor tribeVisitor) {
    }
}
