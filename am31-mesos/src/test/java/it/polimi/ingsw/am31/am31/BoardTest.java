package it.polimi.ingsw.am31.am31;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.CardNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.testUtils.TestUtilities;
import it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;
import static it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver.createLogObserver;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    private CharacterCard ccard;
    private BuildingCard bcard;

    @BeforeEach
    void setup () throws IOException {
        this.board = new Board(2, TestUtilities.getJSONGameResources().getOfferCards());
        this.ccard = createArtist().era(1).build();
        this.bcard = createBuilding().era(1).cost(1).prestigePointsGained(1).build();

        GameObserversSet obsSet = new GameObserversSet();
        LogObserver logObs = createLogObserver()
                .cardLineUpdate()
                .build();
        obsSet.addObserver(logObs);
        board.setObserverHandler(obsSet);
    }
    @Test
    void TestShouldAddUpper () {
        //should be empty, then we add a card, then check
        assertTrue(board.getUpperLine().isEmpty());
        board.addUpper(ccard);
        assertEquals(board.getUpperLine().getFirst(),ccard);
        board.addUpper(bcard);
        assertEquals(board.getUpperBLine().getFirst(),bcard);
    }
    @Test
    void TestShouldAddLower () {

        assertTrue(board.getUpperLine().isEmpty());
        Card c1 = createHunter().build();
        assertFalse(board.getUnderLine().contains(c1));
        board.addLower(c1);
        assertTrue(board.getUnderLine().contains(c1));

        BuildingCard b1 = createBuilding().build();
        assertFalse(board.getUnderLine().contains(b1));
        assertFalse(board.getUnderBLine().contains(b1));
        board.addLower(b1);
        assertTrue(board.getUnderLine().contains(b1));
        assertTrue(board.getUnderBLine().contains(b1));
    }

    @Test
    void TestShouldMoveLowerCardsAndBuildings() {
        assertTrue(board.getUpperLine().isEmpty());
        assertTrue(board.getUpperBLine().isEmpty());
        Card i1 = createInventor().build();
        BuildingCard b1 = createBuilding().era(2).build();
        BuildingCard b2 = createBuilding().era(1).build();
        board.addLower(b2);
        board.addUpper(i1);
        board.addUpper(b1);
        assertFalse(board.getUnderLine().contains(i1));
        assertFalse(board.getUnderBLine().contains(b1));
        assertTrue(board.getUnderBLine().contains(b2));
        board.moveLowerTribes();
        board.moveLowerBuildings();
        assertTrue(board.getUnderLine().contains(i1));
        assertTrue(board.getUnderBLine().contains(b1));
        assertFalse(board.getUnderBLine().contains(b2));
    }

    @Test
    void TestShouldDrawFromUpper () {
        board.addUpper(ccard);
        assertTrue(board.getUpperLine().contains(ccard));
        try {
            board.drawFromUpper(ccard);
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage());
        }
        assertFalse(board.getUpperLine().contains(ccard));
    }

    @Test
    void TestShouldDrawBuildingFromLower () {
        board.addLower(bcard);
        assertTrue(board.getUnderBLine().contains(bcard));
        try {
            board.drawFromLower(bcard);
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage());
        }
        assertFalse(board.getUnderBLine().contains(bcard));
    }

    @Test
    void TestShouldReturnPickable () {
        Card e1 = createPaintingEvent().build();
        board.addUpper(e1);
        assertFalse(board.upperLineHasPickable());
        board.addUpper(createBuilding().build());
        assertTrue(board.upperLineHasPickable());
        assertFalse(board.underLineHasCharacters());
        assertFalse(board.underLineHasPickable());
        board.addLower(createHunter().build());
        assertTrue(board.underLineHasCharacters());
    }

}
