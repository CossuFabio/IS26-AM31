package it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Artist;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class ArtistFluentBuilder implements FluentBuilder<Artist> {

    private String cardId;
    private int era;
    private int minPlayers;

    public ArtistFluentBuilder(){
        reset();
    }

    public ArtistFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public ArtistFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public ArtistFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "a1";
        this.era = 1;
        this.minPlayers = GameConstants.MIN_PLAYERS;
    }

    @Override
    public Artist build() {
        Artist toReturn = new Artist(cardId, era, minPlayers);
        reset();
        return toReturn;
    }

}
