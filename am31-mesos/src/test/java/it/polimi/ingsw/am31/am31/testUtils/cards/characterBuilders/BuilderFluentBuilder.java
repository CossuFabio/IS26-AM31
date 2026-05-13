package it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Builder;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class BuilderFluentBuilder implements FluentBuilder<Builder> {

    private String cardId;
    private int era;
    private int minPlayers;
    private int prestigePoints;
    private int discount;

    public BuilderFluentBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.cardId = "b1";
        this.era = 1;
        this.minPlayers = GameConstants.MIN_PLAYERS;
        this.discount = 0;
        this.prestigePoints = 0;
    }

    public BuilderFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public BuilderFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public BuilderFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public BuilderFluentBuilder prestigePoints(int prestigePoints) {
        this.prestigePoints = prestigePoints;
        return this;
    }

    public BuilderFluentBuilder discount(int discount) {
        this.discount = discount;
        return this;
    }

    @Override
    public Builder build() {
        Builder toReturn = new Builder(cardId, era, minPlayers, prestigePoints, discount);
        reset();
        return toReturn;
    }

}
