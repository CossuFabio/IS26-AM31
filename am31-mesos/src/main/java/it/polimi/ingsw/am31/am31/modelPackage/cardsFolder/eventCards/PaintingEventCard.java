package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

/**
 * Represents an instance of a Painting Event card
 */

public class PaintingEventCard extends EventCard {

    private final int minArtist;

    @JsonCreator
    public PaintingEventCard(
            @JsonProperty("cardId") String cardId,
            @JsonProperty("era") int era,
            @JsonProperty("minArtist")int minArtist,
            @JsonProperty("prestigePointsMalus") int prestigePointsMalus,
            @JsonProperty("prestigePointsBonus") int prestigePointsBonus){
        super(cardId, era);
        this.minArtist = minArtist;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
    }

    /**
     * Awards players prestige points bonus if they have a number of artists greater or equal to the minimum artists required,
     * otherwise the player loses prestige points.
     * @param players the list of all players in the game
     */
    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolvePainters(minArtist, prestigePointsMalus, prestigePointsBonus); });
    }

    /**
     * @return The minimum number of artists required to gain points instead of losing them
     */
    public int getMinArtist() {
        return minArtist;
    }

    @Override
    public String toString(){
        return "EventCard type: PaintEvent - Era: " + era +
                " - Minimum number of artists: " + minArtist +
                " - Prestige points bonus: " + prestigePointsBonus + " - Prestige points malus : " + prestigePointsMalus;
    }
    public void acceptVisit(TribeVisitor visitor){
        visitor.visit(this);
    }
}
