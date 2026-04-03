package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Shaman;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RitualEventCardTest {
    private RitualEventCard card;
    private List<Player> players;
    private Shaman shaman;

    @BeforeEach
    void setUp() {
        int stars = 2;
        int era = 1;
        this.shaman = new Shaman("dummy", era,2, stars);
        this.card= new RitualEventCard("dummy", 1,2,3);
        this.players = new ArrayList<>();
        players.add(new Player("BLUE", Color.BLUE));
        players.get(0).addCard(shaman);
        players.get(0).addCard(shaman);
        //should have 4 stars and win and have 3pp
        players.add(new Player("RED", Color.RED));
        players.get(1).addCard(shaman);
        //should have 2 stars and keep them
        players.add(new Player("YELLOW", Color.YELLOW));
        players.add(new Player("WHITE", Color.WHITE));
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