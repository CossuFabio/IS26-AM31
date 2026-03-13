package it.polimi.ingsw.am31.am31.cards;

public class SustainEventCard extends EventCard{
    public void SustainEventCard(int prestigePointsMalus){
        this.prestigePointsMalus = prestigePointsMalus;
        this.prestigePointsBonus = 0;
        this.priority = 2;
    }

    @Override
    public void resolve() {
    }
}
