package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

import java.util.List;

public class PaintingEventCard extends EventCard {
    private int minArtist;

    public void PaintingEventCard(int minArtist, int prestigePointsMalus, int prestigePointsBonus){
        this.minArtist = minArtist;
        this.priority = 1;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolvePainters(minArtist, prestigePointsMalus, prestigePointsBonus); });
    }
}
