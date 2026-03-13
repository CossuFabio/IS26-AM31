package it.polimi.ingsw.am31.am31.cards;

public abstract class EventCard extends Card {
    protected int prestigePointsBonus;
    protected int prestigePointsMalus;
    protected int priority;

    public void EventCard(int prestigePointsBonus, int prestigePointsMalus, int priority){
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
        this.priority = priority;
    }
    public void resolve(){
    }

    public int getPriority() {
        return priority;
    }

    public int getPrestigePointsBonus() {
        return prestigePointsBonus;
    }

    public int getPrestigePointsMalus() {
        return prestigePointsMalus;
    }
}
