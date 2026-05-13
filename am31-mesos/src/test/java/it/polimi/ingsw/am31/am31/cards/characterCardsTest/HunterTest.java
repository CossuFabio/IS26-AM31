package it.polimi.ingsw.am31.am31.cards.characterCardsTest;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static org.junit.jupiter.api.Assertions.*;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
class HunterTest {


    Hunter hunterNoMark = createHunter().era(1).minPlayers(2).mark(false).build();


    Hunter hunterMark = createHunter().era(2).minPlayers(2).mark(true).build();


    @Test
    void TestShouldGetMark() {

        assertFalse(hunterNoMark.getMark());
        assertTrue(hunterMark.getMark());
    }

    @Test
    void TestShouldDoOnPick() {

        Player player = createPlayer().color(null).name("Test").build();

        int startingFood = player.getFood();
        int startingPrestigePoints = player.getPrestigePoints();

        player.addCard(hunterNoMark);

        assertEquals(startingFood, player.getFood());
        assertEquals(startingPrestigePoints, player.getPrestigePoints());

        player.addCard(hunterMark);
        assertEquals(startingFood+1, player.getFood());
        assertEquals(startingPrestigePoints, player.getPrestigePoints());


    }

    @Test
    void TestShouldAcceptVisit() {

        CountVisitor visitor = new CountVisitor();

        int startingHunter = visitor.getHunters();

        hunterNoMark.acceptVisit(visitor);
        hunterMark.acceptVisit(visitor);

        assertEquals(startingHunter + 2, visitor.getHunters());


    }
//    @Test
//    void TestShouldSetMark () {
//        assertFalse(hunterNoMark.getMark());
//        hunterNoMark.setMark(true);
//        assertTrue(hunterNoMark.getMark());
//    }
}