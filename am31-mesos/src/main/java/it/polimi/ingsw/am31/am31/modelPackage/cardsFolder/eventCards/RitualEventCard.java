package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

/**
 * Represents an instance of a Shamanic Ritual card
 */

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


    /**
     * Players with the fewest ritual stars lose prestige points; in case of draw, more than one player lose points.
     * Players with the most ritual stars gain prestige points; in case of draw, more than one player gain points.
     * In the case all players have the same amount of ritual stars, they all lose and win the ritual at the same time.
     * @param players the list of all players in the game
     */
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
