package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

/**
 * Represents an instance of a Hunt Event card
 * Food bonus and prestige points values are awarded per hunter in the tribe.
 */
public class HuntEventCard extends EventCard {
    private final int foodBonus;

    @JsonCreator
    public HuntEventCard(
            @JsonProperty("cardId") String cardId,
            @JsonProperty("era") int era,
            @JsonProperty("foodBonus") int foodBonus,
            @JsonProperty("prestigePointsBonus") int prestigePointsBonus){
        super(cardId, era);
        this.foodBonus = foodBonus;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = 0;
    }

    /**
     * Awards players bonus food and prestige points based on the number of hunters in their tribe.
     * @param players the list of all players in the game
     */
    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolveHunt(foodBonus, prestigePointsBonus);});
    }

    @Override
    public String toString(){
        return "EventCard type: HuntEvent - Era: " + era + " - Food bonus per hunter: " + foodBonus + " - Prestige points per hunter: " + prestigePointsBonus;
    }

    /** @return the food bonus gained for each hunter*/
    public int getFoodBonus() {
        return foodBonus;
    }


    public void acceptVisit(TribeVisitor visitor){
        visitor.visit(this);
    }
}
