package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

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
        this.priority = 1+era;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolveHunt(foodBonus, prestigePointsBonus);});
    }

    @Override
    public String toString(){
        return "EventCard type: HuntEvent - Era: " + era + " - Food bonus per hunter: " + foodBonus + " - Prestige points per hunter: " + prestigePointsBonus;
    }

}
