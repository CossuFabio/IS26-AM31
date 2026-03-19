package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

import java.util.List;

public class SustainEventCard extends EventCard{
    public SustainEventCard(int prestigePointsMalus){
        super();
        this.prestigePointsMalus = prestigePointsMalus;
        this.prestigePointsBonus = 0;
        this.priority = 2;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolveSustain(prestigePointsMalus);});
    }
}
