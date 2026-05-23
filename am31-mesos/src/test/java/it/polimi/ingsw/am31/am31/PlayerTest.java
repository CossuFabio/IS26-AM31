package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Shaman;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.RED;
import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.WHITE;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;
    private final String nickname = "Test";
    private final Color color = Color.BLUE;
    private final int ritualStars = 3;
    @BeforeEach
    //This test creates a player
    void createPlayerAndTribe(){
        this.player = createPlayer().name(nickname).color(color).build();
        player.addCard(createShaman().stars(ritualStars).build());
        player.addCard(createInventor().icon(IconEnum.ARROW).build());

        player.setObserverHandler(new GameObserversSet());

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

        //Initial check
        assertEquals(starting, player.getFood(), "Initial value for food: 0");

        // Adding food
        player.editFood(10);
        assertEquals(bonus, player.getFood(), "Food increased by " + bonus);

        // Removing food
        player.editFood(malus);
        assertEquals(0, player.getFood(), "Food decreased below 0, cannot be negative");

        // Other changes
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
        assertEquals(player.getRitualStars(), ritualStars);
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

    @Test
    void utilitiesTest(){

        //Testing equals
        Player p1 = createPlayer().name("Fede").color(RED).build();
        Player p2 = createPlayer().name("edeF").build();
        Player p3 = createPlayer().name("Fede").color(WHITE).build();
        Shaman shaman = createShaman().build();

        assertNotEquals(p1, p2);
        assertEquals(p1, p1);
        assertEquals(p1,p3);
        assertNotEquals(p2, shaman);
        assertNotEquals(p1, null);

        //Testing hashcode
        assertEquals(p1.getNickname().hashCode(), p1.hashCode());

    }

    @Test
    void getBuildingsTest(){

        BuildingCard b1 = createBuilding().cardId("b1").build();
        BuildingCard b2 = createBuilding().cardId("b1").build();
        BuildingCard b3 = createBuilding().cardId("b1").build();

        List<BuildingCard> buildings = List.of(b1, b2,b3);

        buildings.forEach(b -> player.addCard(b));
        List<BuildingCard> playerBuildings = player.getBuildings();

        assertTrue(
                buildings.size() == playerBuildings.size() &&
                        playerBuildings.containsAll(buildings) &&
                        buildings.containsAll(playerBuildings)

        );

    }

    @Test
    void bonusDraw() {
        assertFalse(player.hasBonusDraw());
        player.addBonusDraw();
        assertTrue(player.hasBonusDraw());
    }


}