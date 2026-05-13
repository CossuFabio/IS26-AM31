package it.polimi.ingsw.am31.am31.visitor;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum.BOAT;
import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum.values;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CountVisitorTest {


    @Test
    void TestVisitHunter() {
        Hunter hunter = createHunter().era(1).mark(true).build();
        CountVisitor visitor = new CountVisitor();
        hunter.acceptVisit(visitor);
        assertEquals(1, visitor.getHunters());
    }

    @Test
    void testVisitFarmer() {
        Farmer farmer = createFarmer().era(1).discount(3).build();
        CountVisitor visitor = new CountVisitor();
        farmer.acceptVisit(visitor);
        assertEquals(1, visitor.getFarmers());

    }

    @Test
    void testVisitShaman() {
        Shaman shaman = createShaman().era(1).stars(3).build();
        CountVisitor visitor = new CountVisitor();
        shaman.acceptVisit(visitor);
        assertEquals(1, visitor.getShamans());
    }

    @Test
    void testVisitArtist() {
        Artist artist = createArtist().era(1).minPlayers(2).build();
        CountVisitor visitor = new CountVisitor();
        artist.acceptVisit(visitor);
        assertEquals(1, visitor.getArtists());
    }

    @Test
    void testVisitInventor() {
        Inventor inventor = createInventor().era(1).icon(BOAT).build();
        CountVisitor visitor = new CountVisitor();
        inventor.acceptVisit(visitor);
        assertEquals(1, visitor.getInventors());
    }

    @Test
    void testVisitBuilder() {
        Builder builder = createBuilder().era(1).prestigePoints(3).discount(3).build();
        CountVisitor visitor = new CountVisitor();
        builder.acceptVisit(visitor);
        assertEquals(1, visitor.getBuilders());
    }

    @Test
    void testVisitEvent() {
        EventCard event = createSustainEvent().era(2).prestigePointsMalus(1).build();
        CountVisitor visitor = new CountVisitor();
        event.acceptVisit(visitor);
        assertEquals(1, visitor.getEvent());
    }

    //We skipped testing the getters methods
}