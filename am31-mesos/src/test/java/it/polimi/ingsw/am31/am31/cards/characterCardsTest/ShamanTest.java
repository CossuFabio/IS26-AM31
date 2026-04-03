package it.polimi.ingsw.am31.am31.cards.characterCardsTest;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Shaman;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShamanTest {
    private Shaman shaman;
    private List<Player> players;
    private final int era = 1;
    private final int stars = 2;
    private String id = "dummy";
    @BeforeEach
    void setUp() {
        this.shaman = new Shaman(id, era, 2,stars);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.getFirst().addCard(shaman);
    }
    @Test
    void TestShouldGetStars() {
        assertEquals(stars, shaman.getStars());
    }

    @Test
    void TestShouldDoOnPick() {
        assertEquals(0,players.getFirst().getRitualStars(),stars);
        players.getFirst().addCard(shaman);
        assertEquals(0,players.getFirst().getRitualStars(),2*stars);
    }

    @Test
    void TestShouldAcceptVisit() {
        CountVisitor visitor = new CountVisitor();
        shaman.acceptVisit(visitor);
        assertEquals(1, visitor.getShamans());
    }
/*    @Test
    void TestShouldSetStars() {
        assertEquals(shaman.getStars(),stars);
        shaman.setStars(stars+1);
        assertEquals(stars+1, shaman.getStars());
    }*/
}