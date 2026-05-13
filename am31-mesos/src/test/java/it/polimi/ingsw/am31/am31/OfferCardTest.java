package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createOfferCard;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static org.junit.jupiter.api.Assertions.*;

class OfferCardTest {
    private OfferCard offerCard;
    private Player player;

    @BeforeEach
    void setUp() {
        this.offerCard = createOfferCard().food(0).drawFromUnder(2).drawFromUpper(0).build();
        this.player = createPlayer().color(Color.BLUE).name("BLUE").build();
    }

    @Test
    void shouldNotBeFree() {
        this.offerCard.setPlayer(player);
        assertFalse(this.offerCard.isFree());
    }

    @Test
    void shouldSetPlayer() {
        this.offerCard.setPlayer(player);
        assertEquals(player, this.offerCard.getPlayer());
    }

    @Test
    void shouldBeFree() {
        this.offerCard.setPlayer(player);
        this.offerCard.free();
        assertTrue(this.offerCard.isFree());
    }

    @Test
    void shouldStillBeFree(){
        this.offerCard.setPlayer(null);
        assertTrue(this.offerCard.isFree());
    }
}