package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

import java.util.List;

public class PaintingEventCard extends EventCard {
    private final int minArtist;
    private final int prestigePointsBonus;
    private final int prestigePointsMalus;
    private final int priority;

    public  PaintingEventCard(int era, int minArtist, int prestigePointsMalus, int prestigePointsBonus){
        this.minArtist = minArtist;
        super(era);
        this.priority = 1+era;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolvePainters(minArtist, prestigePointsMalus, prestigePointsBonus); });
    }
//TODO TESTING
    public int getMinArtist() {
        return minArtist;
    }
}
