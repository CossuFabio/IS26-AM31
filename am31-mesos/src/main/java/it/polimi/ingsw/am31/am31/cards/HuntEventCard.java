package it.polimi.ingsw.am31.am31.cards;

public class HuntEventCard extends EventCard {
    private int foodBonus;

    public void HuntEventCard(int foodBonus, int prestigePointsBonus){
        this.foodBonus = foodBonus;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = 0;
        this.priority = 1;
    }

    @Override
    public void resolve() {
    }
}
