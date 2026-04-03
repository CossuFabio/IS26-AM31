package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card card;
    @BeforeEach
    void setUp() {
        this.card= new SustainEventCard("dummy", 1,2);
    }
    @Test
    void TestShiouldgetEra() {
        assertEquals(1,card.getEra());
    }
    @Test
    void TestShiouldacceptVisit() {
        return;
    }

    //@Test
   // void TestShouldSetMinPlayers() {
   //     card.setMinPlayers(2);
    //assertEquals(2,card.getMinPlayers());
    //}
   // @Test
   // void TestShouldGetMinPlayers() {
    //    card.setMinPlayers(2);
   //     assertEquals(2,card.getMinPlayers());
  //  }



}