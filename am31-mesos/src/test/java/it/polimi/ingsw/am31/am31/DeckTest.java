package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.Artist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void TestisEmpty() {
        BuildingDeck deck = new BuildingDeck(4);
        assertEquals(deck.isEmpty(),deck.eraDeck.isEmpty());
    }
// TODO TEST THE TEST, IF IT DOESNT WORK, CUT AND PASTE INTO CONCRETE CLASSES
    @Test
    void TestShoulddraw() {
        BuildingDeck deck= new BuildingDeck(4);
        deck.eraDeck.add(new Artist(1));
        assertEquals(new Artist(1),deck.draw());
    }
}