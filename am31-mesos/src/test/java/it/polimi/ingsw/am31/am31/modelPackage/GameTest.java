package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.BuildingDeck;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void TestShouldAddPlayer() {
    }

    @Test
    void TestShouldRemovePlayer() {
    }

    @Test
    void TestShouldGameStart() {
    }

    @Test
    void TestShouldGameEnd() {
    }

    @Test
    void TestShouldResetGame() {
    }

    @Test
    void TestShouldStartRound() {
    }

    @Test
    void TestShouldEndRound() {
    }
    //TODO RUN this
    @Test
    void TestShouldChangeEra() throws IOException {
        Game game = new Game(3, new GameResources(
                new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier()
        ));

        game.getBoard().addUpper(new BuildingCard(1,1,1,null));
        game.getBoard().addLower(new BuildingCard(2,2,2,null));
        assertEquals(2, game.getBoard().getUnderBLine().getFirst().getEra());
        assertEquals(1, game.getBoard().getUpperBLine().getFirst().getEra()); //we check if the cards were added
        //change era should move the lower and delete the lower card
        game.changeEra();
         //we check if the cards were moved and removed
        assertEquals(1, game.getBoard().getUnderBLine().getFirst().getEra());
        assertEquals(1,game.getBoard().getUnderBLine().size());
    }

    @Test
    void TestShouldPlayerChoice() {
    }

    @Test
    void TestShouldPlayerDrawFromUpper() {
    }

    @Test
    void TestShouldPlayerDrawFromTop() {
    }

    @Test
    void TestShouldGetTurnOrder() {
    }

    @Test
    void TestShouldGetBoard() {
    }
}