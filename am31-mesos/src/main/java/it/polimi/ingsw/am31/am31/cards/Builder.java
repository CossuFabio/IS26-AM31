package it.polimi.ingsw.am31.am31.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;

public class Builder extends CharacterCard {
    private final int prestigePoints;
    private final int discount;
    @JsonCreator
    public Builder(@JsonProperty("era")int era,
                   @JsonProperty("minPlayers") int minPlayers,
                   @JsonProperty("prestigePoints")int prestigePoints,
                   @JsonProperty("discount")int discount) {
        super(era, minPlayers);
        this.prestigePoints = prestigePoints;
        this.discount = discount;
    }

    @Override
    public int getBuildingDiscount() {
        return discount;
    }

    @Override
    public int getPrestigePoints() {
        return prestigePoints;
    }

    @Override
    public void acceptVisit(TribeVisitor visitor) {
        visitor.visit(this);
    }
}
