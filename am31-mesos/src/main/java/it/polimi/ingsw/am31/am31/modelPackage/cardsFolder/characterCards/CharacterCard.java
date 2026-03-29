package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

public abstract class CharacterCard extends Card implements IPickable {


    protected final int minPlayers;

    protected CharacterCard(int era, int minPlayers) {
        super(era);
        this.minPlayers = minPlayers;
    }

    @Override
    public void onPick(Player player) {}

    @Override
    public int getMinPlayers(){return this.minPlayers; }

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

    public void acceptVisit (TribeVisitor tribeVisitor) {}

    @Override
    public void addToPlayer(Player player){
        player.addCard(this);
    }



}
