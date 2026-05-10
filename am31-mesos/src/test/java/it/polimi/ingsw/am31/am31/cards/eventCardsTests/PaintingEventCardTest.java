package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Artist;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.PaintingEventCard;
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
        this.eventCard = new PaintingEventCard("dummy", 1, 2, 3, 3);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.getFirst().addCard(new Artist("dummy", 1,2));
        players.getFirst().addCard(new Artist("dummy", 1, 2));
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
        assertEquals(EventCard.PriorityClass.HIGH, eventCard.getPriorityClass());
        }
    }