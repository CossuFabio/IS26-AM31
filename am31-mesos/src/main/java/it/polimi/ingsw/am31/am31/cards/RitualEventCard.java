package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Player;

import java.util.List;

public class RitualEventCard extends EventCard {
    public void RitualEventCard(int prestigePointsMalus, int prestigePointsBonus){
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
        this.priority = 1;
    }

    public void resolve(List<Player> players) {
        int minStars = players.getFirst().getRitualStars();
        int maxStars = minStars;
        for(Player player : players){
            if(player.getRitualStars() < minStars){
                minStars = player.getRitualStars();
            }
            if(player.getRitualStars() > maxStars){
                maxStars = player.getRitualStars();
            }
        }

        for(Player player : players){
            if(player.getRitualStars() == minStars) player.loseRitual(prestigePointsMalus);
            if(player.getRitualStars() == maxStars) player.winRitual(prestigePointsMalus);
        }

    }
}
