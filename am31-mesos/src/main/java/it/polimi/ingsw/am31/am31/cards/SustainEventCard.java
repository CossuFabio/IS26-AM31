package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

import java.util.List;

public class SustainEventCard extends EventCard{
    public SustainEventCard(int era, int prestigePointsMalus){
        super(era);
        this.prestigePointsMalus = prestigePointsMalus;
        this.prestigePointsBonus = 0;
        this.priority = 20+era;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolveSustain(prestigePointsMalus);});
    }

}
