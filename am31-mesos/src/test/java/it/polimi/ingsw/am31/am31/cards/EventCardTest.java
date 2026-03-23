package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.visitor.TribeVisitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestDescriptor;

import static org.junit.jupiter.api.Assertions.*;

class EventCardTest {
    private EventCard eventCard;
    @BeforeEach
    void  setUp() {
        this.eventCard = new SustainEventCard(1, 2);
    }

    @Test
    void resolve() {
        //no sense in testing generic resolve (?)
    }

    @Test
    void TestShouldgetPriority() {
        assertEquals(21, this.eventCard.getPriority());
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