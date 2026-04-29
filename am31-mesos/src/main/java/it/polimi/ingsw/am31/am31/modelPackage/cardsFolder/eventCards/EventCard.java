package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

import java.util.List;

public abstract class EventCard extends Card {
    protected int prestigePointsBonus;
    protected int prestigePointsMalus;
    protected int priority;

    protected EventCard(String cardID, int era) {
        super(cardID, era);
    }

    public abstract void resolve(List<Player> players);

    public int getPriority() {
        return priority;
    }

    public int getPrestigePointsBonus() {
        return prestigePointsBonus;
    }

    public int getPrestigePointsMalus() {
        return prestigePointsMalus;
    }

    public void acceptVisit(TribeVisitor visitor){
            visitor.visit(this);
        }


}
