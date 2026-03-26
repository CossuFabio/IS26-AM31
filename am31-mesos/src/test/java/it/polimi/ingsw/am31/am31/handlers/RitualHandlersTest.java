package it.polimi.ingsw.am31.am31.handlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualLose.NoMalusRitualLoseStrategy;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.ritualWin.DoubleWinRitualStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RitualHandlersTest {

    private Player player;
    private int food;


    @BeforeEach
    void setUp() {
        this.player = new Player("Test", Color.BLACK);
    }

    @Test
    void shouldHandleNormalWin(){
        int startingPoints = player.getPrestigePoints();
        int bonusPoints = 10;
        this.player.winRitual(bonusPoints);
        assertEquals(startingPoints + bonusPoints, player.getPrestigePoints());
    }

    @Test
    void shouldHandleDoubleWin(){

        int startingPoints = player.getPrestigePoints();
        int bonusPoints = 10;
        player.addRitualWinEffect(DoubleWinRitualStrategy::new);

        this.player.winRitual(bonusPoints);
        assertEquals(startingPoints + 2*bonusPoints, player.getPrestigePoints());
    }

    @Test
    void shouldHandleNormalLose(){
        int startingPoints = player.getPrestigePoints();
        int malusPoints = 10;
        this.player.loseRitual(malusPoints);
        assertEquals(startingPoints -  malusPoints, player.getPrestigePoints());
    }

    @Test
    void shouldHandleNoLose(){
        this.player.addRitualLoseEffect(NoMalusRitualLoseStrategy::new);
        int startingPoints = player.getPrestigePoints();
        int malusPoints = 10;
        this.player.loseRitual(malusPoints);
        assertEquals(startingPoints, player.getPrestigePoints());
    }

}
