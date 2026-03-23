package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    private Deck deck;

    @BeforeEach
    void TestDeck () {
        this.deck = new BuildingDeck(3);
    }
    @Test
    void TestShouldBeEmpty() {
        int temp = deck.eraDeck.size();
        for(int i=0;i<temp;i++)
            deck.draw();
        assertTrue(deck.isEmpty());
    }
    @Test
    void TestShouldDraw() {
        BuildingCard temp = (BuildingCard) deck.draw();
        //first card should be era 1
        assertEquals(1,temp.getEra());
        int size = deck.eraDeck.size();
        for(int i=0; i<size; i++) {
            deck.draw();
        };
        assertThrows(IllegalStateException.class, () -> deck.draw());

    }
}