package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

import java.util.List;

public class PaintingEventCard extends EventCard {
    private final int minArtist;
    public  PaintingEventCard(int era, int minArtist, int prestigePointsMalus, int prestigePointsBonus){
        this.minArtist = minArtist;
        super(era);
        this.priority = 1+era;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
    }

    //for JSON use
    public PaintingEventCard() {}

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolvePainters(minArtist, prestigePointsMalus, prestigePointsBonus); });
    }

    public int getMinArtist() {
        return minArtist;
    }
}
