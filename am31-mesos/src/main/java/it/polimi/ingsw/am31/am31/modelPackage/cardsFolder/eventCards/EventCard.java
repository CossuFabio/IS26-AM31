package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

import java.util.List;

public abstract class EventCard extends Card {

    public enum PriorityClass {
        HIGH, LOW
    }

    protected int prestigePointsBonus;
    protected int prestigePointsMalus;
    protected PriorityClass priorityClass;

    protected EventCard(String cardID, int era) {
        super(cardID, era);
    }

    public abstract void resolve(List<Player> players);

    public PriorityClass getPriorityClass() {
        return priorityClass;
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

    @Override
    public boolean isCharacter() {return false;}
}
