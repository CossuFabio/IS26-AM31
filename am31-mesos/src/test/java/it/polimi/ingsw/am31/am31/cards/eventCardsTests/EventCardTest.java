package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;

import static org.junit.jupiter.api.Assertions.*;

class EventCardTest {
    private EventCard eventCard;

    @BeforeEach
    void setUp() {
        this.eventCard = createSustainEvent().era(1).prestigePointsMalus(2).build();
    }


    @Test
    void TestShouldgetPrestigePointsBonus() {
        assertEquals(0, this.eventCard.getPrestigePointsBonus());
    }
    @Test
    void TestShouldgetPrestigePointsMalus() {
        assertEquals(2, this.eventCard.getPrestigePointsMalus());
    }

    @Test
    void TestShouldAcceptVisit() {
        CountVisitor visitor = new CountVisitor();
        eventCard.acceptVisit(visitor);
        assertEquals(1, visitor.getEvent());
    }
}