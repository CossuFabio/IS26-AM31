package it.polimi.ingsw.am31.am31.cards;

public class PaintingEventCard extends EventCard {
    private int minArtist;

    public void PaintingEventCard(int minArtist, int prestigePointsMalus, int prestigePointsBonus){
        this.minArtist = minArtist;
        this.priority = 1;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
    }

    @Override
    public void resolve() {

    }
}
