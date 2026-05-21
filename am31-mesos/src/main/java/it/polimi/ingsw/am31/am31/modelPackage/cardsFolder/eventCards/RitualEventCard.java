package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

public class RitualEventCard extends EventCard {

    @JsonCreator
    public RitualEventCard(
            @JsonProperty("cardId") String cardId,
            @JsonProperty("era") int era,
            @JsonProperty("prestigePointsMalus") int prestigePointsMalus,
            @JsonProperty("prestigePointsBonus") int prestigePointsBonus){
        super(cardId, era);
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
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
            if(player.getRitualStars() == maxStars) player.winRitual(prestigePointsBonus);
        }

    }
    public void acceptVisit(TribeVisitor visitor){
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "EventCard type: RitualEvent - Era: " + era +
                " - Prestige points for win: " + prestigePointsBonus +
                " - Prestige points for lose: " + prestigePointsMalus;
    }

}
