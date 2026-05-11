package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectIdsConstants;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.*;
import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;
import it.polimi.ingsw.am31.am31.view.LocalState.*;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEndedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TUIResultsTest {
    private TextUserInterface textUserInterface;

    @BeforeEach
    public void setup() {
        LocalGameState state = new LocalGameState();
        List<Card> cards1 = new ArrayList<>();
        List<Card> cards2 = new ArrayList<>();
        LocalPlayerState test1 = new LocalPlayerState("test", Color.BLACK);
        LocalPlayerState test2 = new LocalPlayerState("test2",Color.WHITE);
        Card h = new Hunter("h1",1,2,true);
        Card f = new Farmer("f1",2,2,3);
        Card i = new Inventor("i1",2,2, IconEnum.BREAD);
        Card a = new Artist("a1", 1, 2);
        Card h2 = new Hunter("h1",1,2,true);
        Card f2 = new Farmer("f1",2,2,3);
        Card i2 = new Inventor("i1",2,2, IconEnum.BREAD);
        state.addPlayer(test1);
        state.addPlayer(test2);
        test1.setPrestigePoints(25);
        test1.setFood(10);
        test2.setPrestigePoints(25);
        test2.setFood(8);
        cards1.add(i);cards1.add(h);cards1.add(f);cards1.add(a);
        cards2.add(h2);cards2.add(f2);cards2.add(i);cards2.add(i2);
        test1.setTribe(cards1);
        test2.setTribe(cards2);
        ViewEventBus eventBus = new ViewEventBus();
            ClientController controller = new ClientController(null, eventBus);
            controller.setLocalNameTest();
            LocalLeaderBoard winner = new LocalLeaderBoard(test1, true);
            LocalLeaderBoard loser = new LocalLeaderBoard(test2, false);
            List<LocalLeaderBoard> leaderBoard =  new ArrayList<>();
            leaderBoard.add(winner);
            leaderBoard.add(loser);
            state.setLeaderboard(leaderBoard);
            textUserInterface = new TextUserInterface(controller, state, eventBus);
            textUserInterface.gameEnded(new GameEndedEvent());
    }

    @Test
    void testShouldShowResultsScene() {

    }
}
