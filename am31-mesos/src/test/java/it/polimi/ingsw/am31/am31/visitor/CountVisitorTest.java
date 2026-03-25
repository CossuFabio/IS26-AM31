package it.polimi.ingsw.am31.am31.visitor;

import it.polimi.ingsw.am31.am31.cards.*;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.cards.IconEnum.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountVisitorTest {


    @Test
    void TestVisitHunter() {
        Hunter hunter = new Hunter(1, 2, true);
        CountVisitor visitor = new CountVisitor();
        hunter.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitFarmer() {
        Farmer farmer = new Farmer(1, 2,3);
        CountVisitor visitor = new CountVisitor();
        farmer.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());

    }

    @Test
    void testVisitShaman() {
        Shaman shaman = new Shaman(1, 2, 3);
        CountVisitor visitor = new CountVisitor();
        shaman.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitArtist() {
        Artist artist = new Artist(1, 2);
        CountVisitor visitor = new CountVisitor();
        artist.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitInventor() {
        Inventor inventor = new Inventor(1,2, BOAT);
        CountVisitor visitor = new CountVisitor();
        inventor.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitBuilder() {
        Builder builder = new Builder(1, 2,3, 3);
        CountVisitor visitor = new CountVisitor();
        builder.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitEvent() {
        EventCard event= new SustainEventCard(2,1);
        CountVisitor visitor = new CountVisitor();
        event.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    //We skipped testing the getters methods
}