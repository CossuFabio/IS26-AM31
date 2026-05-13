package it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class HunterFluentBuilder implements FluentBuilder<Hunter> {

    private String cardId;
    private int era;
    private int minPlayers;
    private boolean mark;

    public HunterFluentBuilder(){
        reset();
    }

    public HunterFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public HunterFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public HunterFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    public HunterFluentBuilder mark(boolean mark) {
        this.mark = mark;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "h1";
        this.era = 1;
        this.minPlayers = GameConstants.MIN_PLAYERS;
        this.mark = false;
    }

    @Override
    public Hunter build() {
        Hunter toReturn = new Hunter(cardId, era, minPlayers, mark);
        reset();
        return toReturn;
    }
}
