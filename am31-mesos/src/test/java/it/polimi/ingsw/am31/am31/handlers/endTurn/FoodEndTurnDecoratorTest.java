package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.FoodEndTurnDecorator;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.IEndTurnHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static org.junit.jupiter.api.Assertions.*;

class FoodEndTurnDecoratorTest {
    private Player player;
    private Function<IEndTurnHandler, IEndTurnHandler> foodEndTurnDecorator;

    @BeforeEach
    void setUp() {
        this.player = createPlayer().name("Test").color(Color.BLACK).build();
    }

    @Test
    void shouldHandleEndTurnAdditionalFood() {
        int playerOrder = 0;
        int nPlayers = 3;
        this.player.addEndTurnEffect(it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.FoodEndTurnDecorator::new);
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(3, player.getFood());
    }

    @Test
    void shouldHandleEndTurnAdditionalNoFood() {
        int playerOrder = 3;
        int nPlayers = 5;
        this.player.addEndTurnEffect(FoodEndTurnDecorator::new);
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(0, player.getFood());
    }

    @Test
    void shouldHandleEndTurnAdditionalFoodFive() {
        int playerOrder = 0;
        int nPlayers = 5;
        this.player.addEndTurnEffect(FoodEndTurnDecorator::new);
        this.player.resolveEndTurn(playerOrder, nPlayers);
        assertEquals(4, player.getFood());
    }
}