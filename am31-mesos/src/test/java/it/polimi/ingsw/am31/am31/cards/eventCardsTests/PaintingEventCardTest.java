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

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createArtist;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createPaintingEvent;
import static org.junit.jupiter.api.Assertions.*;

class PaintingEventCardTest {
    private PaintingEventCard eventCard;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        this.eventCard = createPaintingEvent().era(1).minArtist(2).prestigePointsBonus(3).prestigePointsMalus(3).build();
        this.players = new ArrayList<>();
        players.add(createPlayer().name("BLUE").color(Color.BLUE).build());
        Artist card = createArtist().era(1).minPlayers(2).build();
        players.getFirst().addCard(card);
        players.getFirst().addCard(card);
        players.add(createPlayer().name("RED").color(Color.RED).build());
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