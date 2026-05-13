package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Shaman;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.*;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static org.junit.jupiter.api.Assertions.*;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
class RitualEventCardTest {
    private RitualEventCard card;
    private List<Player> players;
    private Shaman shaman;

    @BeforeEach
    void setUp() {
        int stars = 2;
        int era = 1;

        this.shaman = createShaman().era(era).stars(stars).build();
        this.card = createRitualEvent().era(1).prestigePointsBonus(3).prestigePointsMalus(2).build();

        this.players = new ArrayList<>();
        players.add(createPlayer().name("BLUE").color(BLUE).build());
        players.getFirst().addCard(shaman);
        players.getFirst().addCard(shaman);
        //should have 4 stars and win and have 3pp
        players.add(createPlayer().name("RED").color(RED).build());
        players.get(1).addCard(shaman);
        //should have 2 stars and keep them
        players.add(createPlayer().name("YELLOW").color(YELLOW).build());
        players.add(createPlayer().name("WHITE").color(WHITE).build());
        //2 losing players, should both have 0-2 pp
    }
    @Test
    void TestShouldResolve() {
        assertEquals(0, players.get(3).getPrestigePoints());
        card.resolve(players);
        assertEquals(-2, players.get(3).getPrestigePoints());
        assertEquals(-2, players.get(2).getPrestigePoints());
        assertEquals(0, players.get(1).getPrestigePoints());
        assertEquals(3, players.get(0).getPrestigePoints());
    }
}