package it.polimi.ingsw.am31.am31.cards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card card;
    @BeforeEach
    void setUp() {
        this.card= new SustainEventCard(1,2);
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