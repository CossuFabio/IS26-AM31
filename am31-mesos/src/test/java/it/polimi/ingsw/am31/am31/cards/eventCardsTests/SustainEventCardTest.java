package it.polimi.ingsw.am31.am31.cards.eventCardsTests;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Artist;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Farmer;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SustainEventCardTest {
    private SustainEventCard card;
    private List<Player> players;
    @BeforeEach
    void setup() {
        int era = 1;
        int malus = 2;
        players = new ArrayList<>();
        card = new SustainEventCard("dummy", era, malus);
        players.add(new Player("BLUE", Color.BLUE));
        players.get(0).addCard(new Artist("dummy", 1,2));
        players.get(0).addCard(new Artist("dummy", 1,2));
        players.get(0).editFood(2);
        //player 0 has 2 food, 2 cost to feed them -> goes to 0
        players.add(new Player("RED", Color.RED));
        players.get(1).addCard(new Artist("dummy", 1,2));
        //player 1 has no food, should lose 2pp go to -2pp
        players.add(new Player("WHITE", Color.WHITE));
        players.get(2).addCard(new Artist("dummy", 1,2));
        players.get(2).addCard(new Farmer("dummy", 1,2));
        players.get(2).editFood(5);
        //player 2 has discount, should pay 0 food and have 5
    }
    @Test
    void resolve() {
        assertEquals(0,players.get(0).getPrestigePoints());
        assertEquals(5,players.get(2).getFood());
        card.resolve(players);
        assertEquals(0,players.get(0).getFood());
        assertEquals(0,players.get(0).getPrestigePoints());
        assertEquals(0,players.get(1).getFood());
        assertEquals(-2,players.get(1).getPrestigePoints());
        assertEquals(5,players.get(2).getFood());
        assertEquals(0,players.get(2).getPrestigePoints());

    }
    @Test
    void TestShouldGetPriority () {
        assertEquals(EventCard.PriorityClass.LOW, card.getPriorityClass());
    }
}