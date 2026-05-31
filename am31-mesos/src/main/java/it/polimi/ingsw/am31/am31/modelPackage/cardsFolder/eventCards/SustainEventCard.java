package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

/**
 * Represents an instance of a Sustain Event card
 */

public class SustainEventCard extends EventCard {

    @JsonCreator
    public SustainEventCard(
            @JsonProperty("cardId") String cardId,
            @JsonProperty("era") int era,
            @JsonProperty("prestigePointsMalus") int prestigePointsMalus){
        super(cardId, era);
        this.prestigePointsMalus = prestigePointsMalus;
        this.prestigePointsBonus = 0;
    }

    /**
     * Each player has to pay one unit of food for each character in their tribe. This amount of food can be discounted
     * by Farmers or bonus effects. If a player cannot afford to pay all the food, they lose prestige points based on the
     * quantity of food that they don't pay multiplied by this card's value of prestigePointsMalus.
     * @param players the list of all players in the game
     */
    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolveSustain(prestigePointsMalus);});
    }

    @Override
    public String toString(){
        return "EventCard type: SustainEvent - Era: " + era + " - Prestige points malus: " + prestigePointsMalus;
    }
    public void acceptVisit(TribeVisitor visitor){
        visitor.visit(this);
    }
}
