package it.polimi.ingsw.am31.am31.cards.characterCardsTest;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HunterTest {

    Hunter hunterNoMark = new Hunter(1 ,2,false);
    Hunter hunterMark = new Hunter(2,2,true);



    @Test
    void TestShouldGetMark() {

        assertEquals(hunterNoMark.getMark(), false);
        assertEquals(hunterMark.getMark(), true);
    }

    @Test
    void TestShouldDoOnPick() {

        Player player = new Player("Test", null);

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