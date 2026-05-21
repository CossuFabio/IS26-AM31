package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

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
