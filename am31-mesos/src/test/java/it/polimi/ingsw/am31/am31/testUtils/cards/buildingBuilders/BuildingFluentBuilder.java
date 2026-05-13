package it.polimi.ingsw.am31.am31.testUtils.cards.buildingBuilders;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.testUtils.FluentBuilder;

import java.util.function.Consumer;

public class BuildingFluentBuilder implements FluentBuilder<BuildingCard> {

    private String cardId;
    private int era;
    private int cost;
    private int prestigePointsGained;
    private Consumer<Player> effect;
    private String description;

    public BuildingFluentBuilder(){
        reset();
    }

    public BuildingFluentBuilder cardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public BuildingFluentBuilder era(int era) {
        this.era = era;
        return this;
    }

    public BuildingFluentBuilder cost(int cost) {
        this.cost = cost;
        return this;
    }

    public BuildingFluentBuilder prestigePointsGained(int prestigePointsGained) {
        this.prestigePointsGained = prestigePointsGained;
        return this;
    }

    public BuildingFluentBuilder effect(Consumer<Player> effect) {
        this.effect = effect;
        return this;
    }

    public BuildingFluentBuilder description(String description){
        this.description = description;
        return this;
    }

    @Override
    public void reset() {
        this.cardId = "bd1";
        this.era = 1;
        this.cost = 0;
        this.prestigePointsGained = 0;
        this.effect = (p -> {});
        this.description = "TEST BUILDING";
    }

    @Override
    public BuildingCard build() {
        BuildingCard toReturn = new BuildingCard(cardId, era, cost, prestigePointsGained, description, effect);
        reset();
        return toReturn;
    }
}
