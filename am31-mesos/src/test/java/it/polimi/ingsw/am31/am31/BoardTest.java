package it.polimi.ingsw.am31.am31;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.Artist;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.testUtils.TestUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;
    private CharacterCard ccard;
    private BuildingCard bcard;

    @BeforeEach
    void setup () throws IOException {
        this.board = new Board(2, TestUtilities.getJSONGameResources().getOfferCards());
        this.ccard = new Artist("dummy", 1,2);
        this.bcard = new BuildingCard ("dummy", 1,1,1,null);
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

    }
    @Test
    void TestShouldMoveLowerTribes () {

    }
    @Test
    void TestShouldMoveLowerBuildings () {

    }

    @Test
    void TestShouldAddBuildingUpper () {

    }
    @Test
    void TestShouldAddBuildingLower () {

    }
    @Test
    void TestShouldDrawFromUpper () {

    }
    @Test
    void TestShouldDrawFromLower () {

    }
    @Test
    void TestShouldShowBoard () {

    }
    @Test
    void TestShouldReturnNextCard () {

    }
    @Test
    void TestShould () {

    }

}
