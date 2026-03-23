package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Color;
import it.polimi.ingsw.am31.am31.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaintingEventCardTest {
    private PaintingEventCard eventCard;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        this.eventCard = new PaintingEventCard(1, 2, 3, 3);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.getFirst().addToTribe(new Artist(1));
        players.getFirst().addToTribe(new Artist(1));
        players.add(new Player("RED", Color.RED));
    }
        @Test
        void TestShouldResolve () {
            assertEquals(0, players.getFirst().getPrestigePoints());
            eventCard.resolve(players);
            assertEquals(6, players.get(0).getPrestigePoints());
            assertEquals(-3, players.get(1).getPrestigePoints());
        }
        @Test
        void TestShouldGetMinArtist () {
        assertEquals(2, eventCard.getMinArtist());
        }
        @Test
        void TestShouldGetPriority () {
        assertEquals(2, eventCard.getPriority());
        }
    }