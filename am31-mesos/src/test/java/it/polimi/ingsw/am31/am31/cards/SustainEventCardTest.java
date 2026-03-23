package it.polimi.ingsw.am31.am31.cards;

import it.polimi.ingsw.am31.am31.Color;
import it.polimi.ingsw.am31.am31.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SustainEventCardTest {
    private SustainEventCard card;
    private List<Player> players;
    @BeforeEach
    void setup() {
        int era = 1;
        int malus = 2;
        card = new SustainEventCard(era, malus);
        players.add(new Player("BLUE", Color.BLUE));
        players.get(0).addToTribe(new Artist(1));
        players.get(0).addToTribe(new Artist(1));
        players.get(0).editFood(2);
        //player 0 has 2 food, 2 cost to feed them -> goes to 0
        players.add(new Player("RED", Color.RED));
        players.get(1).addToTribe(new Artist(1));
        //player 1 has no food, should lose 2pp go to -2pp
        players.add(new Player("BLUE", Color.BLUE));
        players.get(2).addToTribe(new Artist(1));
        players.get(2).addToTribe(new Farmer(1,3));
        players.get(2).editFood(5);
        //player 2 has discount, should pay 0 food and have 5
    }
    @Test
    void resolve() {
        assertEquals(0,players.getFirst().getPrestigePoints());
        assertEquals(5,players.get(3).getFood());
        card.resolve(players);

    }
    @Test
    void TestShouldGetPriority () {
        assertEquals(21, card.getPriority());
    }
}