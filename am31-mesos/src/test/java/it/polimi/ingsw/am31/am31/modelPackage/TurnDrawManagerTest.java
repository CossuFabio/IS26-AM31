package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonOfferSupplier;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

public class TurnDrawManagerTest {

    int numPlayers = 5;
    Board board ;
    TurnDrawManager turnDrawManager ;

    @BeforeEach
    void setUp() throws IOException {
        board = new Board(numPlayers, new JsonOfferSupplier().getResources());
        turnDrawManager = new TurnDrawManager(board);
    }



}
