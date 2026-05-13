package it.polimi.ingsw.am31.am31.testUtils.board;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class OfferCardFluentBuilder implements FluentBuilder<OfferCard> {

    private String offerCardId;
    private int food;
    private int drawFromUnder;
    private int drawFromUpper;
    private int minPlayers;

    public OfferCardFluentBuilder(){
        reset();
    }

    public OfferCardFluentBuilder offerCardId(String offerCardId) {
        this.offerCardId = offerCardId;
        return this;
    }

    public OfferCardFluentBuilder food(int food) {
        this.food = food;
        return this;
    }

    public OfferCardFluentBuilder drawFromUnder(int drawFromUnder) {
        this.drawFromUnder = drawFromUnder;
        return this;
    }

    public OfferCardFluentBuilder drawFromUpper(int drawFromUpper) {
        this.drawFromUpper = drawFromUpper;
        return this;
    }

    public OfferCardFluentBuilder minPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
        return this;
    }

    @Override
    public void reset() {
        this.offerCardId = "A";
        this.food = 0;
        this.drawFromUpper = 0;
        this.drawFromUnder = 0;
        this.minPlayers = GameConstants.MIN_PLAYERS;
    }

    @Override
    public OfferCard build() {
        OfferCard offerCard = new OfferCard(offerCardId, food, drawFromUnder, drawFromUpper, minPlayers);
        reset();
        return offerCard;
    }
}
