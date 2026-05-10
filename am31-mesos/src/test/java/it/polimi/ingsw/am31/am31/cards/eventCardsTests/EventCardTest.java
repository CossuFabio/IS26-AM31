package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventCardTest {
    private EventCard eventCard;
    @BeforeEach
    void  setUp() {
        this.eventCard = new SustainEventCard("dummy", 1, 2);
    }

    @Test
    void resolve() {
        //no sense in testing generic resolve (?)
    }

    @Test
    void TestShouldgetPriority() {
        assertEquals(EventCard.PriorityClass.LOW, this.eventCard.getPriorityClass());
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