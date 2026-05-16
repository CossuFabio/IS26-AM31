package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createLobby;
import static it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver.createLogObserver;
import static org.junit.jupiter.api.Assertions.*;

class TurnOrderTest {

    static int nPlayers = 5;
    private TurnOrder testTurnOrder;

    @BeforeEach
    public void setUp(){
        testTurnOrder = new TurnOrder(nPlayers);
        List<Player> lobby = createLobby(nPlayers);

        //Helps debugging
        LogObserver obs = createLogObserver()
                .turnOrderUpdate()
                .scoresUpdate()
                .build();
        GameObserversSet obsSet = new GameObserversSet();
        obsSet.addObserver(obs);
        testTurnOrder.setObserverHandler(obsSet);

    }

    private void isLast(Player p){

    }



    @Test
    public void shouldNotGiveFood(){
        //This function tests the setPlayerFirstRound(Player): checks the correct order
        //of insertion and the fact that food do not change

    }

}