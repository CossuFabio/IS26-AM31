package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static org.junit.jupiter.api.Assertions.*;

class DefaultEndTurnHandlerTest {

    private Player player;

    @BeforeEach
    void setUp() {this.player = createPlayer().name("Test").color(Color.BLACK).build();}

    @Test
    void shouldHandleEndTurnNoEffect() {
        int playerOrder = 1;
        int nPlayers = 3;
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(0, player.getFood());
    }

    @Test
    void shouldHandleEndTurnAdditionalFood() {
        int playerOrder = 1;
        int nPlayers = 4;
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(1, player.getFood());
    }

    @Test
    void shouldHandleEndTurnPPLoss() {
        int playerOrder = 2;
        int nPlayers = 3;
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(-2, player.getPrestigePoints());
    }

    @Test
    void shouldHandleEndTurn() {
        int playerOrder = 0;
        int nPlayers = 5;
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(3, player.getFood());
    }


}