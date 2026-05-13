package it.polimi.ingsw.am31.am31.testUtils.cards.eventBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.HuntEventCard;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

public class HuntEventFluentBuilder implements FluentBuilder<HuntEventCard> {

    private String cardId;
    private int era;
    private int foodBonus;
    private int prestigePointsBonus;

    public HuntEventFluentBuilder(){
        reset();
    }

    public HuntEventFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public HuntEventFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public HuntEventFluentBuilder foodBonus(int foodBonus) {
        this.foodBonus = foodBonus;
        return this;
    }

    public HuntEventFluentBuilder prestigePointsBonus(int prestigePointsBonus) {
        this.prestigePointsBonus = prestigePointsBonus;
        return this;
    }


    @Override
    public void reset() {
        this.cardId = "he1";
        this.era = 1;
        this.foodBonus = 0;
        this.prestigePointsBonus = 0;
    }

    @Override
    public HuntEventCard build() {
        HuntEventCard toReturn = new HuntEventCard(cardId, era, foodBonus, prestigePointsBonus);
        reset();
        return toReturn;
    }
}
