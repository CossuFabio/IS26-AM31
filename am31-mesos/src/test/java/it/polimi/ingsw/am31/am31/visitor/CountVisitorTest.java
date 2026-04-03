package it.polimi.ingsw.am31.am31.visitor;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum.BOAT;
import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum.values;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountVisitorTest {


    @Test
    void TestVisitHunter() {
        Hunter hunter = new Hunter("dummy", 1, 2, true);
        CountVisitor visitor = new CountVisitor();
        hunter.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitFarmer() {
        Farmer farmer = new Farmer("dummy", 1, 2,3);
        CountVisitor visitor = new CountVisitor();
        farmer.acceptVisit(visitor);
        assertEquals(1, visitor.getFarmers());

    }

    @Test
    void testVisitShaman() {
        Shaman shaman = new Shaman("dummy", 1, 2, 3);
        CountVisitor visitor = new CountVisitor();
        shaman.acceptVisit(visitor);
        assertEquals(1, visitor.getShamans());
    }

    @Test
    void testVisitArtist() {
        Artist artist = new Artist("dummy", 1, 2);
        CountVisitor visitor = new CountVisitor();
        artist.acceptVisit(visitor);
        assertEquals(1, visitor.getArtists());
    }

    @Test
    void testVisitInventor() {
        Inventor inventor = new Inventor("dummy", 1,2, BOAT);
        CountVisitor visitor = new CountVisitor();
        inventor.acceptVisit(visitor);
        assertEquals(1, visitor.getInventors());
    }

    @Test
    void testVisitBuilder() {
        Builder builder = new Builder("dummy", 1, 2,3, 3);
        CountVisitor visitor = new CountVisitor();
        builder.acceptVisit(visitor);
        assertEquals(1, visitor.getBuilders());
    }

    @Test
    void testVisitEvent() {
        EventCard event= new SustainEventCard("dummy", 2,1);
        CountVisitor visitor = new CountVisitor();
        event.acceptVisit(visitor);
        assertEquals(1, visitor.getEvent());
    }

    //We skipped testing the getters methods
}