package it.polimi.ingsw.am31.am31.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Hunter;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent.BonusHunterHandleDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.BLACK;
import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createBuilding;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.createHunter;
import static org.junit.jupiter.api.Assertions.*;

class DefaultHuntHandlerTest {

    private Player player;
    private int food;

    private final int hunterNumberBeforeMark = 5;
    private final int hunterNumberAfterMark = 10;

    @BeforeEach
    void setUp() {
        this.player = createPlayer().name("Test").color(BLACK).build();

        for(int i = 0; i<hunterNumberBeforeMark; i++){
            player.addCard(createHunter().mark(false).build());
        }

        player.addCard(createHunter().mark(true).build());
        food = player.getFood();

        assertEquals(hunterNumberBeforeMark, food);

        for(int i = 0; i<hunterNumberAfterMark - hunterNumberBeforeMark - 1; i++){
            player.addCard(createHunter().mark(false).build());
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


        this.player.addCard(createBuilding().cost(1).prestigePointsGained(1).effect(player -> player.addHuntEffect(BonusHunterHandleDecorator::new)).build());
        this.player.resolveHunt(1, 1);

        //Bonus handler => +x prestigePoints, +x food (x = number of hunters in tribe)
        //Expected food is food - 1 (marked hunter isn't counted in his effect) + 2*hunterNumberAfterMark (the total number of hunters)
        assertEquals(player.getFood(), food - 1 +hunterNumberAfterMark +hunterNumberAfterMark);
        assertEquals(hunterNumberAfterMark+hunterNumberAfterMark, player.getPrestigePoints());

    }

}