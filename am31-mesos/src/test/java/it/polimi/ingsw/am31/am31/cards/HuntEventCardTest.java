package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Color;
import it.polimi.ingsw.am31.am31.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HuntEventCardTest {
    private HuntEventCard eventCard;
    private List<Player> players;
    @BeforeEach
    void setUp() {
        this.eventCard = new HuntEventCard(1,2,3);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.getFirst().addCard(new Hunter(1,false));
        players.getFirst().addCard(new Hunter(1,false));
    }
    @Test
    void TestShouldTestingResolve() {
        assertEquals(players.getFirst().getFood(),0);
        eventCard.resolve(players);
        assertEquals(players.getFirst().getFood(),4);
    }
    @Test
    void TestShouldTestingGetPriority () {
        assertEquals(2, eventCard.getPriority());
    }
}