package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.InvalidPlayersNumberException;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static java.util.Comparator.comparingInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void TestShouldResolveEvents() throws IOException {
        List<OfferCard> track = new ArrayList<OfferCard>();
        track.add(new OfferCard("A", 2, 1, 1, 2));
        Board board = new Board(3, track);

        PriorityQueue<EventCard> eventQueue = new PriorityQueue<>(
                comparingInt(EventCard::getPriority)
        );
        CountVisitor visitor = new CountVisitor();
        ArrayList<Card> templine = new ArrayList<>(board.getUnderLine());

        int tempevent = 0;
        while (!templine.isEmpty()) {
            templine.getFirst().acceptVisit(visitor);
            if (visitor.getEvent() > tempevent) {
                eventQueue.add((EventCard) templine.getFirst());  //Safe explicit cast to EventCard
                tempevent = visitor.getEvent();
            }
            templine.removeFirst();
        }
    }

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
    void TestShouldChangeEra() throws IOException, InvalidPlayersNumberException {
        Game game = new Game(3, new GameResources(
                new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier()
        ));

        game.getBoard().addUpper(new BuildingCard("dummy", 1,1,1,null));
        game.getBoard().addLower(new BuildingCard("dummy", 2,2,2,null));
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