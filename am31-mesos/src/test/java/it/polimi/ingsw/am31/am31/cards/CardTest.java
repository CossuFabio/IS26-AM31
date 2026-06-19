package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;

class CardTest {
    private Card card;

    @BeforeEach
    void setUp() {
        this.card = createSustainEvent().era(1).prestigePointsMalus(2).build();
    }
    @Test
    void TestShiouldgetEra() {
        assertEquals(1,card.getEra());
    }

    @Test
    void TestShiouldacceptVisit() {
        return;
    }





}