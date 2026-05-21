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

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class SustainEventCardTest {
    private SustainEventCard card;
    private List<Player> players;
    @BeforeEach
    void setup() {
        int era = 1;
        int malus = 2;
        players = new ArrayList<>();
        this.card = createSustainEvent().era(era).prestigePointsMalus(malus).build();
        players.add(createPlayer().color(Color.BLUE).name("Blue").build());
        Artist artist = createArtist().era(era).minPlayers(2).build();
        players.get(0).addCard(artist);
        players.get(0).addCard(artist);
        players.get(0).editFood(2);
        //player 1 has 2 food, 2 cost to feed them -> goes to 0
        players.add(createPlayer().color(Color.RED).name("RED").build());
        players.get(1).addCard(artist);
        //player 2 has no food, should lose 2pp go to -2pp
        players.add(createPlayer().color(Color.WHITE).name("WHITE").build());
        players.get(2).addCard(createArtist().build());
        players.get(2).addCard(createFarmer().discount(3).build());
        players.get(2).editFood(5);
        //player 3 has discount, should pay 0 food and have 5
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
}