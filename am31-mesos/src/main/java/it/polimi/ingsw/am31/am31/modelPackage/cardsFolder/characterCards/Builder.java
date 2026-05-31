package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;

/**
 * Represents an instance of a Builder card
 */
public class Builder extends CharacterCard {
    private final int prestigePoints;
    private final int discount;
    @JsonCreator
    public Builder(
            @JsonProperty("cardId") String cardId,
            @JsonProperty("era")int era,
            @JsonProperty("minPlayers") int minPlayers,
            @JsonProperty("prestigePoints")int prestigePoints,
            @JsonProperty("discount")int discount) {
        super(cardId, era, minPlayers);
        this.prestigePoints = prestigePoints;
        this.discount = discount;
    }

    /** @return food discount this builder provides on building purchases */
    @Override
    public int getBuildingDiscount() {
        return discount;
    }

    /** @return prestige points this builder gives at end of game */
    @Override
    public int getPrestigePoints() {
        return prestigePoints;
    }

    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString(){
        return "CharacterCard type: Builder - Era: " + era + " - Minimum Players: " + minPlayers + " - Prestige points gained: " +
                prestigePoints + " - Buildings cost discount: "+ discount;
    }

}
