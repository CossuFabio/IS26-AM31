package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.Color;
import it.polimi.ingsw.am31.am31.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class FoodEndTurnDecoratorTest {
    private Player player;
    private Function<IEndTurnHandler, IEndTurnHandler> FoodEndTurnDecorator;

    @BeforeEach
    void setUp() {
        this.player = new Player("Test", Color.BLACK);
    }

    @Test
    void shouldHandleEndTurnAdditionalFood() {
        int playerOrder = 0;
        int nPlayers = 3;
        this.player.addEndTurnEffect(FoodEndTurnDecorator::new);
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