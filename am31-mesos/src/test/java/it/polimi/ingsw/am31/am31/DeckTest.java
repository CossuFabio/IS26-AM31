package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EmptyDeckException;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.BuildingDeck;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    private Deck deck;

    @BeforeEach
    void TestDeck () throws IOException {
        this.deck = new BuildingDeck(3, TestUtilities.getJSONGameResources().getBuildingCards());
    }
    @Test
    void TestShouldBeEmpty() throws EmptyDeckException {
        int temp = deck.getSize();
        for(int i=0;i<temp;i++)
            deck.draw();
        assertTrue(deck.isEmpty());
    }
    @Test
    void TestShouldDraw() throws EmptyDeckException {
        BuildingCard temp = (BuildingCard) deck.draw();
        //first card should be era 1
        assertEquals(1,temp.getEra());
        int size = deck.getSize();
        for(int i=0; i<size; i++) {
            deck.draw();
        };
        assertThrows(EmptyDeckException.class, () -> deck.draw());

    }
}