package it.polimi.ingsw.am31.am31.testUtils.cards.eventBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class RitualEventFluentBuilder implements FluentBuilder<RitualEventCard> {

    private String cardId;
    private int era;
    private int prestigePointsMalus;
    private int prestigePointsBonus;

    public RitualEventFluentBuilder(){
        reset();
    }

    public RitualEventFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public RitualEventFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public RitualEventFluentBuilder prestigePointsMalus(int prestigePointsMalus) {
        this.prestigePointsMalus = prestigePointsMalus;
        return this;
    }

    public RitualEventFluentBuilder prestigePointsBonus(int prestigePointsBonus) {
        this.prestigePointsBonus = prestigePointsBonus;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "re1";
        this.era = 1;
        this.prestigePointsMalus = 0;
        this.prestigePointsBonus = 0;
    }

    @Override
    public RitualEventCard build() {
        RitualEventCard toReturn = new RitualEventCard(cardId, era, prestigePointsMalus, prestigePointsBonus);
        reset();
        return toReturn;
    }
}
