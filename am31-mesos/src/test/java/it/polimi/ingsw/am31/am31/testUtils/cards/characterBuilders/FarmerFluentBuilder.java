package it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Farmer;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class FarmerFluentBuilder implements FluentBuilder<Farmer> {

    private String cardId;
    private int era;
    private int minPlayers;
    private int discount;

    public FarmerFluentBuilder(){
        reset();
    }

    public FarmerFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public FarmerFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public FarmerFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public FarmerFluentBuilder discount(int discount) {
        this.discount = discount;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "f1";
        this.era = 1;
        this.minPlayers = GameConstants.MIN_PLAYERS;
        this.discount = 0;
    }

    @Override
    public Farmer build() {
        Farmer toReturn = new Farmer(cardId, era, minPlayers, discount);
        reset();
        return toReturn;
    }

}
