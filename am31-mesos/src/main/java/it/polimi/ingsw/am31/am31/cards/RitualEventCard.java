package it.polimi.ingsw.am31.am31.cards;

public class RitualEventCard extends EventCard {
    public void RitualEventCard(int prestigePointsMalus, int prestigePointsBonus){
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
        this.priority = 1;
    }

    @Override
    public void resolve() {
    }
}
