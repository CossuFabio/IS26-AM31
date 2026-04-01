package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

public class PaintingEventCard extends EventCard {

    private final int minArtist;

    @JsonCreator
    public PaintingEventCard(
            @JsonProperty("era") int era,
            @JsonProperty("minArtist")int minArtist,
            @JsonProperty("prestigePointsMalus") int prestigePointsMalus,
            @JsonProperty("prestigePointsBonus") int prestigePointsBonus){
        super(era);
        this.minArtist = minArtist;
        this.priority = 1+era;
        this.prestigePointsBonus = prestigePointsBonus;
        this.prestigePointsMalus = prestigePointsMalus;
    }

    public void resolve(List<Player> players) {
        players.forEach((Player p) -> {p.resolvePainters(minArtist, prestigePointsMalus, prestigePointsBonus); });
    }

    public int getMinArtist() {
        return minArtist;
    }

    @Override
    public String toString(){
        return "EventCard type: PaintEvent - Era: " + era +
                " - Minimum number of artists: " + minArtist +
                " - Prestige points bonus: " + prestigePointsBonus + " - Prestige points malus : " + prestigePointsMalus;
    }
}
