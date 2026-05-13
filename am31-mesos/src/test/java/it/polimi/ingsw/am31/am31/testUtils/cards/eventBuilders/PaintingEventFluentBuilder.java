package it.polimi.ingsw.am31.am31.testUtils.cards.eventBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.PaintingEventCard;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class PaintingEventFluentBuilder implements FluentBuilder<PaintingEventCard> {

    private String cardId;
    private int era;
    private int minArtist;
    private int prestigePointsMalus;
    private int prestigePointsBonus;

    public PaintingEventFluentBuilder(){
        reset();
    }


    public PaintingEventFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public PaintingEventFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public PaintingEventFluentBuilder minArtist(int minArtist) {
        this.minArtist = minArtist;
        return this;
    }

    public PaintingEventFluentBuilder prestigePointsMalus(int prestigePointsMalus) {
        this.prestigePointsMalus = prestigePointsMalus;
        return this;
    }

    public PaintingEventFluentBuilder prestigePointsBonus(int prestigePointsBonus) {
        this.prestigePointsBonus = prestigePointsBonus;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "pe1";
        this.era = 1;
        this.minArtist = 0;
        this.prestigePointsMalus = 0;
        this.prestigePointsBonus = 0;
    }

    @Override
    public PaintingEventCard build() {
        PaintingEventCard toReturn = new PaintingEventCard(cardId, era, minArtist, prestigePointsMalus, prestigePointsBonus);
        reset();
        return toReturn;
    }
}
