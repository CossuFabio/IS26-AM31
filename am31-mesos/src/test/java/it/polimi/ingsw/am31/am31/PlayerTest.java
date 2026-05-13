package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Player player;
    private final String nickname = "Test";
    private final Color color = Color.BLUE;

    @BeforeEach
    //This test creates a player
    void createPlayerAndTribe(){
        this.player = createPlayer().name(nickname).color(color).build();
        player.addCard(createShaman().stars(3).build());
        player.addCard(createInventor().icon(IconEnum.ARROW).build());

    }

    @Test
    void testGetNickname() {
        assert(player.getNickname().equals(nickname));
    }

    @Test
    void testGetColor() {
        assert(player.getColor() == color);
    }

    @Test
    void testEditFood() {
        int bonus = 10;
        int malus = -20;
        int starting = 0;
        // Controllo iniziale
        assertEquals(starting, player.getFood(), "Initial value for food: 0");

        // Aggiungo cibo
        player.editFood(10);
        assertEquals(bonus, player.getFood(), "Food increased by " + bonus);

        // Tolgo cibo
        player.editFood(malus);
        assertEquals(0, player.getFood(), "Food decreased below 0, cannot be negative");

        // Altre modifiche
        player.editFood(50);
        player.editFood(-20);
        assertEquals(30, player.getFood(), "Final food must be 30 after increase of 50 and decrease of 20");
    }

    @Test
    void getFood() {
        player.editFood(10);
        assertEquals(player.getFood(), 10);
    }

    @Test
    void editPrestigePoints() {
        int starting = player.getPrestigePoints();
        player.editPrestigePoints(10);
        assertEquals(player.getPrestigePoints(), 10);
        player.editPrestigePoints(-20);
        assertEquals(player.getPrestigePoints(), -10);
    }



    @Test
    void getRitualStars() {
        //The player has a 3 stars shaman
        assertEquals(player.getRitualStars(), 3);
    }

    @Test
    void increaseStars() {
        int stars = player.getRitualStars();
        player.increaseStars(1);
        assertEquals(stars + 1, player.getRitualStars());
    }

    @Test
    void addCard() {
        Hunter h = createHunter().mark(false).build();
        player.addCard(h);
        assertEquals(player.getTribe().contains(h), true);
    }




}