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
import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.BLUE;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createHuntEvent;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createHunter;
import static org.junit.jupiter.api.Assertions.*;

class HuntEventCardTest {
    private HuntEventCard eventCard;
    private List<Player> players;
    @BeforeEach
    void setUp() {

        this.eventCard = createHuntEvent().era(1).foodBonus(2).prestigePointsBonus(3).build();
        this.players = new ArrayList<>();

        players.add(createPlayer().name("BLUE").color(BLUE).build());
        players.getFirst().addCard(createHunter().mark(false).build() );
        players.getFirst().addCard(createHunter().mark(false).build());
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