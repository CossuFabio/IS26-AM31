package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfferCardTest {
    private OfferCard offerCard;

    @BeforeEach
    void setUp() {this.offerCard = new OfferCard("dummy", 0, 2, 0, 2);}

    @Test
    void shouldNotBeFree() {
        Player player = new Player("BLUE", Color.BLUE);
        this.offerCard.setPlayer(player);
        assertFalse(this.offerCard.isFree());
    }

    @Test
    void shouldSetPlayer() {
        Player player = new Player("BLUE", Color.BLUE);
        this.offerCard.setPlayer(player);
        assertEquals(player, this.offerCard.getPlayer());
    }

    @Test
    void shouldBeFree() {
        Player player = new Player("BLUE", Color.BLUE);
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