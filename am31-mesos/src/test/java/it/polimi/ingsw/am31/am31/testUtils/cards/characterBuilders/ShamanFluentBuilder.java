package it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Shaman;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class ShamanFluentBuilder implements FluentBuilder<Shaman> {

    private String cardId;
    private int era;
    private int minPlayers;
    private int stars;

    public ShamanFluentBuilder(){
        reset();
    }

    @Override
    public void reset(){
        this.cardId = "s1";
        this.era = 1;
        this.minPlayers = GameConstants.MIN_PLAYERS;
        this.stars = 0;
    }

    public ShamanFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public ShamanFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public ShamanFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public ShamanFluentBuilder stars(int stars) {
        this.stars = stars;
        return this;
    }

    @Override
    public Shaman build(){
        Shaman toReturn = new Shaman(cardId, era, minPlayers, stars);
        reset();
        return toReturn;
    }

}
