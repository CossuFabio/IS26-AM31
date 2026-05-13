package it.polimi.ingsw.am31.am31.testUtils.cards.eventBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class SustainEventFluentBuilder implements FluentBuilder<SustainEventCard> {


    private String cardId;
    private int era;
    private int prestigePointsMalus;

    public SustainEventFluentBuilder(){
        reset();
    }

    public SustainEventFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public SustainEventFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public SustainEventFluentBuilder prestigePointsMalus(int prestigePointsMalus) {
        this.prestigePointsMalus = prestigePointsMalus;
        return this;
    }


    @Override
    public void reset() {
        this.cardId = "se1";
        this.era = 1;
        this.prestigePointsMalus = 0;
    }

    @Override
    public SustainEventCard build() {
        SustainEventCard toReturn = new SustainEventCard(cardId, era, prestigePointsMalus);
        reset();
        return toReturn;
    }
}
