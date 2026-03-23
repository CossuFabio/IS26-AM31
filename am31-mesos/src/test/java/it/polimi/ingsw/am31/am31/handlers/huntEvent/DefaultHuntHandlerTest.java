package it.polimi.ingsw.am31.am31.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.Color;
import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Hunter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultHuntHandlerTest {

    private Player player;
    private int food;

    private int hunterNumberBeforeMark = 5;
    private int hunterNumberAfterMark = 10;
    @BeforeEach
    void setUp() {
        this.player = new Player("Test", Color.BLACK);

        for(int i = 0; i<hunterNumberBeforeMark; i++){
            player.addToTribe(new Hunter(1, false));
        }

        player.addToTribe(new Hunter(1, true));
        food = player.getFood();

        assertEquals(hunterNumberBeforeMark, food);

        for(int i = 0; i<hunterNumberAfterMark - hunterNumberBeforeMark - 1; i++){
            player.addToTribe(new Hunter(1, false));
        }

    }


    @Test
    void shouldHandleDefaultHunt() {
        this.player.resolveHunt(1, 1);
        assertEquals(food+hunterNumberAfterMark, player.getFood());
        assertEquals(hunterNumberAfterMark, player.getPrestigePoints());
    }

    @Test
    void shouldHandleBonustHuntWithHunter() {
        this.player.addToBuildings(new BuildingCard(1, 1, 1, (player) -> player.addHuntEffect(BonusHunterHandleDecorator::new)));
        this.player.resolveHunt(1, 1);

        //Bonus handler => +x prestigePoints, +x food (x = number of hunters in tribe)
        assertEquals(food+hunterNumberAfterMark+hunterNumberAfterMark, player.getFood());
        assertEquals(hunterNumberAfterMark+hunterNumberAfterMark, player.getPrestigePoints());

    }

}