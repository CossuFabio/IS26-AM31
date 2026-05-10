package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.HuntEventCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard.PriorityClass.HIGH;
import static org.junit.jupiter.api.Assertions.*;

class HuntEventCardTest {
    private HuntEventCard eventCard;
    private List<Player> players;
    @BeforeEach
    void setUp() {
        this.eventCard = new HuntEventCard("dummy", 1,2,3);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.getFirst().addCard(new Hunter("h1", 1,2,false));
        players.getFirst().addCard(new Hunter("h1", 1,2,false));
    }
    @Test
    void TestShouldTestingResolve() {
        assertEquals(players.getFirst().getFood(),0);
        eventCard.resolve(players);
        assertEquals(players.getFirst().getFood(),4);
    }
    @Test
    void TestShouldTestingGetPriority () {
        assertEquals(HIGH, eventCard.getPriorityClass());
    }
}