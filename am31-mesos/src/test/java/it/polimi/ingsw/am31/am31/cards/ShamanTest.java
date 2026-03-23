package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Color;
import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;
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

    @BeforeEach
    void setUp() {
        this.shaman = new Shaman(era, stars);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.getFirst().addToTribe(shaman);
    }
    @Test
    void TestShouldGetStars() {
        assertEquals(stars, shaman.getStars());
    }

    @Test
    void TestShouldDoOnPick() {
        assertEquals(0,players.getFirst().getRitualStars(),stars);
        players.getFirst().addToTribe(shaman);
        assertEquals(0,players.getFirst().getRitualStars(),2*stars);
    }

    @Test
    void TestShouldAcceptVisit() {
        CountVisitor visitor = new CountVisitor();
        shaman.acceptVisit(visitor);
        assertEquals(1, visitor.getShamans());
    }
    @Test
    void TestShouldSetStars() {
        assertEquals(shaman.getStars(),stars);
        shaman.setStars(stars+1);
        assertEquals(stars+1, shaman.getStars());
    }
}