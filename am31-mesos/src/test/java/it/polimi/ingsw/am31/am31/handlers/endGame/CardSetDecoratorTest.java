package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.CardSetDecorator;
import it.polimi.ingsw.am31.am31.testUtils.emptyHandlers.EmptyEndGameHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardSetDecoratorTest {



    private Player owner;
    private EmptyEndGameHandler baseHandler;
    private int startingPrestigePoints;

    @BeforeEach
    void setUp() {
        owner = createPlayer().build();
        baseHandler = new EmptyEndGameHandler();
        startingPrestigePoints = owner.getPrestigePoints();
    }

    @Test
    void testOneCompleteSet() {
        owner.addCard(createArtist().build());
        owner.addCard(createBuilder().build());
        owner.addCard(createFarmer().build());
        owner.addCard(createShaman().build());
        owner.addCard(createHunter().build());
        owner.addCard(createInventor().build());

        new CardSetDecorator(baseHandler).handleEndGame(owner);

        assertEquals(startingPrestigePoints + CardSetDecorator.PRESTIGE_POINTS_BONUS, owner.getPrestigePoints());
    }

    @Test
    void testIncompleteSet() {
        owner.addCard(createArtist().build());
        owner.addCard(createBuilder().build());
        owner.addCard(createFarmer().build());

        new CardSetDecorator(baseHandler).handleEndGame(owner);

        assertEquals(startingPrestigePoints, owner.getPrestigePoints());
    }

    @Test
    void testMultipleSets() {
        int sets = 3;
        for (int i = 0; i < sets; i++) {
            owner.addCard(createArtist().build());
            owner.addCard(createBuilder().build());
            owner.addCard(createFarmer().build());
            owner.addCard(createShaman().build());
            owner.addCard(createHunter().build());
            owner.addCard(createInventor().build());
        }

        new CardSetDecorator(baseHandler).handleEndGame(owner);

        assertEquals(startingPrestigePoints + sets *CardSetDecorator.PRESTIGE_POINTS_BONUS, owner.getPrestigePoints());
    }

    @Test
    void testMinimumDeterminesSetCount() {
        // 3 artists, 1 of everything else => minimum = 1 => 6 points
        for (int i = 0; i < 3; i++) owner.addCard(createArtist().build());

        owner.addCard(createBuilder().build());
        owner.addCard(createFarmer().build());
        owner.addCard(createShaman().build());
        owner.addCard(createHunter().build());
        owner.addCard(createInventor().build());

        new CardSetDecorator(baseHandler).handleEndGame(owner);

        assertEquals(startingPrestigePoints + CardSetDecorator.PRESTIGE_POINTS_BONUS, owner.getPrestigePoints());
    }

}