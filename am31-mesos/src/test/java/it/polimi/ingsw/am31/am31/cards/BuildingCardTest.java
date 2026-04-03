package it.polimi.ingsw.am31.am31.cards;


import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildingCardTest {
    private BuildingCard card;
    private Player player;
    @BeforeEach
    void setUp() {
        this.card = new BuildingCard ("dummy", 1, 2, 5, (Player player) -> player.increaseStars(3));
        this.player = new Player("BLUE", Color.BLUE);
    }
    @Test
    void TestShouldGetPrestigePointsGained() {
        assertEquals(5, card.getPrestigePointsGained());
    }

        @Test
    void TestShouldGetCost() {
        assertEquals(2, card.getCost());
    }

         @Test
    void TestShouldaActivateEffect() {
        assertEquals(0,player.getRitualStars());
        card.onPick(player);
        assertEquals(3,player.getRitualStars());
    }

}