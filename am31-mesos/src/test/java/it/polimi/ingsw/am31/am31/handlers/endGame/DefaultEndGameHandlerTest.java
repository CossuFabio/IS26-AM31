package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.DefaultEndGameHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createPlayer;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultEndGameHandlerTest {

    private Player owner;
    private DefaultEndGameHandler handler;
    private int startingPrestigePoints;

    @BeforeEach
    void setUp() {
        owner = createPlayer().build();
        handler = new DefaultEndGameHandler();
        startingPrestigePoints = owner.getPrestigePoints();
    }

    @Test
    void testBuildingPoints() {
        owner.addCard(createBuilding().prestigePointsGained(3).build());
        owner.addCard(createBuilding().prestigePointsGained(5).build());

        handler.handleEndGame(owner);

        assertEquals(startingPrestigePoints + 8, owner.getPrestigePoints());
    }

    @Test
    void testArtistsPairBonus() {
        for (int i = 0; i < 4; i++) owner.addCard(createArtist().build());

        handler.handleEndGame(owner);

        assertEquals(startingPrestigePoints + 2 * DefaultEndGameHandler.PRESTIGE_POINTS_ARTISTS_PAIR,
                owner.getPrestigePoints());
    }

    @Test
    void testArtistsPairOddNumber() {
        for (int i = 0; i < 3; i++) owner.addCard(createArtist().build());

        handler.handleEndGame(owner);


        assertEquals(startingPrestigePoints + DefaultEndGameHandler.PRESTIGE_POINTS_ARTISTS_PAIR,
                owner.getPrestigePoints());
    }

    @Test
    void testInventorsBonus() {

        owner.addCard(createInventor().icon(IconEnum.BREAD).build());
        owner.addCard(createInventor().icon(IconEnum.TOTEM).build());

        handler.handleEndGame(owner);

        assertEquals(startingPrestigePoints + 4, owner.getPrestigePoints());
    }

    @Test
    void testInventorsNoBonusWithEmptyIcons() {
        owner.addCard(createInventor()
                .icon(IconEnum.EMPTY).build()); // icon = EMPTY => not counted in the set
        owner.addCard(createInventor().build());

        handler.handleEndGame(owner);

        assertEquals(startingPrestigePoints, owner.getPrestigePoints());
    }

    @Test
    void testEmptyTribe() {
        handler.handleEndGame(owner);

        assertEquals(startingPrestigePoints, owner.getPrestigePoints());
    }
}