package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EverybodyPlayedException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.IncorrectMethodCallException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerAlreadyInTurnOrderException;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.testUtils.TestUtilities.createLobby;
import static it.polimi.ingsw.am31.am31.testUtils.testObservers.LogObserver.createLogObserver;
import static org.junit.jupiter.api.Assertions.*;

class TurnOrderTest {

    static int nPlayers = 5;
    private TurnOrder testTurnOrder;
    List<Player> lobby;

    @BeforeEach
    public void setUp(){
        //Init
        testTurnOrder = new TurnOrder(nPlayers);
        lobby = createLobby(nPlayers);

        //Helps debugging
        LogObserver obs = createLogObserver()
                .turnOrderUpdate()
                .scoresUpdate()
                .build();
        GameObserversSet obsSet = new GameObserversSet();
        obsSet.addObserver(obs);
        testTurnOrder.setObserverHandler(obsSet);

    }

    private void skipFirstPlacing(){
        for(Player p : lobby){
            testTurnOrder.setPlayerFirstRound(p);
        }

        for(Player p : lobby){
            testTurnOrder.goToNextPlayer();
        }

    }

    @Test
    public void testSetPlayerFirstRound(){

        //First: check if the TurnOrder is correctly initialized empty
        assertTrue(testTurnOrder.getOrder().stream().allMatch(p -> p == null));

        lobby.forEach(p -> testTurnOrder.setPlayerFirstRound(p));


        //Test of order is respected
        assertEquals(lobby.size(), testTurnOrder.getOrder().size());

        //The two lists match all positions
        boolean areEquals = true;
        for(int i = 0; i< lobby.size(); i++){
            if(!lobby.get(i).equals(testTurnOrder.getOrder().get(i))){
                areEquals = false;
                break;
            }
        }

        assertTrue(areEquals);

    }


    @Test
    public void testSetPlayer(){
        skipFirstPlacing();
        //First: check if the TurnOrder is correctly initialized empty
        assertTrue(testTurnOrder.getOrder().stream().allMatch(p -> p == null));

        lobby.forEach(p -> testTurnOrder.setPlayer(p));


        //Test of order is respected
        assertEquals(lobby.size(), testTurnOrder.getOrder().size());

        //The two lists match all positions
        boolean areEquals = true;
        for(int i = 0; i< lobby.size(); i++){
            if(!lobby.get(i).equals(testTurnOrder.getOrder().get(i))){
                areEquals = false;
                break;
            }
        }

        assertTrue(areEquals);

    }

    @Test
    public void testMaintainsOrder(){
        skipFirstPlacing();
        //Place first player on the OfferCard
        testTurnOrder.setPlayer(lobby.getFirst());

        boolean areEquals;
        for(int i = 1; i< lobby.size(); i++){
            areEquals = true;

            for(int j = 0; j<i; j++){
                if(!testTurnOrder.getOrder().get(j).equals(lobby.get(j))) areEquals = false;
            }
            assertTrue(areEquals);
            for(int j = i+1; j<testTurnOrder.getOrder().size(); j++){
                if(testTurnOrder.getOrder().get(j) != null) areEquals = false;
            }
            assertTrue(areEquals);
            testTurnOrder.setPlayer(lobby.get(i));
        }

    }

    @Test
    public void shouldThrowDuplicate(){
        skipFirstPlacing();
        testTurnOrder.setPlayer(lobby.getFirst());

        //Assert that the exception is thrown both in first round and others
        assertThrows(PlayerAlreadyInTurnOrderException.class, () ->{
            testTurnOrder.setPlayer(lobby.getFirst());
        });

    }

    @Test
    public void shouldThrowDuplicateFirstRound(){
        testTurnOrder.setPlayerFirstRound(lobby.getFirst());
        assertThrows(PlayerAlreadyInTurnOrderException.class, () ->{
            testTurnOrder.setPlayerFirstRound(lobby.getFirst());
        });
    }


    @Test
    public void correctPlayOrder(){
        skipFirstPlacing();
        lobby.forEach(p->testTurnOrder.setPlayer(p));
        lobby.forEach( p-> {
                assertEquals(p, testTurnOrder.getPlayerActing());
                assertFalse(testTurnOrder.everybodyPlayed());
                testTurnOrder.goToNextPlayer();

                }
        );

        //Since each player placed his totem, everybody placed is true
        assertTrue(testTurnOrder.everybodyPlayed());

        //Assert that the TurnOrder is Empty
        assertThrows(IncorrectMethodCallException.class, ()->{testTurnOrder.goToNextPlayer();});

    }

    @Test
    public void canHandleMoreRounds(){
        skipFirstPlacing();
        //Test if no IndexOutOfBounds is thrown
        for(int i = 0; i< GameConstants.ROUNDS_NUMBER; i++){
            lobby.forEach(p->testTurnOrder.setPlayer(p));
            lobby.forEach( p-> {
                        assertEquals(p, testTurnOrder.getPlayerActing());
                        assertFalse(testTurnOrder.everybodyPlayed());
                        testTurnOrder.goToNextPlayer();

                    }
            );

            //Since each player placed his totem, everybody placed is true
            assertTrue(testTurnOrder.everybodyPlayed());

            //Assert that the TurnOrder is Empty
            assertThrows(IncorrectMethodCallException.class, ()->{testTurnOrder.goToNextPlayer();});

        }

    }

    @Test
    public void cannotSetOutsideTotemPhase(){
        skipFirstPlacing();
        //TurnOrder full => TotemPhase starts => cannot add players to turnorder
        lobby.forEach(p ->testTurnOrder.setPlayer(p));

        assertThrows(IncorrectMethodCallException.class, () -> testTurnOrder.setPlayer(lobby.getFirst()));
        assertThrows(IncorrectMethodCallException.class, () -> testTurnOrder.setPlayerFirstRound(lobby.getFirst()));
    }

    @Test
    public void noActingPlayerOutsideTotemPhase(){
        skipFirstPlacing();
        //TurnOrder full => TotemPhase starts
        lobby.forEach(p ->testTurnOrder.setPlayer(p));

        //Sets action phase
        for(int i = 0; i<lobby.size(); i++){
            testTurnOrder.goToNextPlayer();
        }

        //Cannot invoke those methods outside Totem Placing
        assertThrows(IncorrectMethodCallException.class, () -> {testTurnOrder.goToNextPlayer();});
        assertThrows(IncorrectMethodCallException.class, () -> {testTurnOrder.getPlayerActing();});

    }

    @Test
    public void testCorrectOrder(){
        //Should call setPlayerFirstRound first
        assertThrows(IncorrectMethodCallException.class, () -> {testTurnOrder.setPlayer(lobby.getFirst());});

        testTurnOrder.setPlayerFirstRound(lobby.getFirst());
        //First placing not yet finished
        assertThrows(IncorrectMethodCallException.class, () -> {testTurnOrder.setPlayer(lobby.getFirst());});

        //Now the first placing is complete
        for(int i = 1; i< lobby.size(); i++){
            testTurnOrder.setPlayerFirstRound(lobby.get(i));
        }

        //Now is ACTION_PHAE
        assertThrows(IncorrectMethodCallException.class,
                ()->{
                    testTurnOrder.setPlayerFirstRound(lobby.getFirst());
                });

        for(Player p: lobby){
            testTurnOrder.goToNextPlayer();
        }

        assertThrows(IncorrectMethodCallException.class, () -> {testTurnOrder.setPlayerFirstRound(lobby.getFirst());});

    }



}