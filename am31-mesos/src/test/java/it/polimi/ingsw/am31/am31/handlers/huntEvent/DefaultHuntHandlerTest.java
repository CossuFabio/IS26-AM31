package it.polimi.ingsw.am31.am31.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.BonusHunterHandleDecorator;
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
            player.addCard(new Hunter(1,2, false));
        }

        player.addCard(new Hunter(1,2,  true));
        food = player.getFood();

        assertEquals(hunterNumberBeforeMark, food);

        for(int i = 0; i<hunterNumberAfterMark - hunterNumberBeforeMark - 1; i++){
            player.addCard(new Hunter(1, 2, false));
        }



    }


    @Test
    void shouldHandleDefaultHunt() {
        this.player.resolveHunt(1, 1);
        assertEquals(food+hunterNumberAfterMark, player.getFood());
        assertEquals(hunterNumberAfterMark, player.getPrestigePoints());
    }

    @Test
    void shouldHandleBonusHuntWithHunter() {

        this.player.addCard(new BuildingCard(1, 1, 1, (player) -> player.addHuntEffect(BonusHunterHandleDecorator::new)));
        this.player.resolveHunt(1, 1);

        //Bonus handler => +x prestigePoints, +x food (x = number of hunters in tribe)
        //Expected food is food - 1 (marked hunter isn't counted in his effect) + 2*hunterNumberAfterMark (the total number of hunters)
        assertEquals(player.getFood(), food - 1 +hunterNumberAfterMark +hunterNumberAfterMark);
        assertEquals(hunterNumberAfterMark+hunterNumberAfterMark, player.getPrestigePoints());

    }

}