package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

import java.util.List;

public abstract class EventCard extends Card {
    protected int prestigePointsBonus;
    protected int prestigePointsMalus;
    protected int priority;

    protected EventCard(int era) {
        super(era);
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
