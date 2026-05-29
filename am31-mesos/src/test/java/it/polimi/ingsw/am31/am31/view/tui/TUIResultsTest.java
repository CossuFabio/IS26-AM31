package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.*;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;
import it.polimi.ingsw.am31.am31.view.localState.*;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEndedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.IconEnum.BREAD;
import static it.polimi.ingsw.am31.am31.testUtils.cards.CardTestUtils.*;

public class TUIResultsTest {
    private TextUserInterface textUserInterface;
    private TUIResults results;
    private LocalGameState gamestate;
    LocalPlayerState test1 ;
    LocalPlayerState test2 ;
    @BeforeEach
    public void setup() {
        gamestate = new LocalGameState();

        List<Card> cards1 = new ArrayList<>();
        List<Card> cards2 = new ArrayList<>();

         test1 = new LocalPlayerState("test", Color.BLACK);
         test2 = new LocalPlayerState("test2",Color.WHITE);

        Card h = createHunter().cardId("h1").era(1).minPlayers(2).mark(true).build();
        Card f = createFarmer().cardId("f1").era(2).minPlayers(2).discount(3).build();
        Card i = createInventor().cardId("i1").era(2).minPlayers(2).icon(BREAD).build();
        Card a = createArtist().cardId("a1").era(1).minPlayers(2).build();
        Card h2 = createHunter().cardId("h1").era(1).minPlayers(2).mark(true).build();
        Card f2 = createFarmer().cardId("f1").era(2).minPlayers(2).discount(3).build();
        Card i2 = createInventor().cardId("i1").era(2).minPlayers(2).icon(BREAD).build();

        gamestate.addPlayer(test1);
        gamestate.addPlayer(test2);

        test1.setPrestigePoints(25);
        test1.setFood(10);
        test2.setPrestigePoints(25);
        test2.setFood(8);

        cards1.add(i);
        cards1.add(h);
        cards1.add(f);
        cards1.add(a);

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
            gamestate.setLeaderboard(leaderBoard);
            textUserInterface = new TextUserInterface(controller, gamestate, eventBus);
            textUserInterface.gameEnded(new GameEndedEvent());
            results = new TUIResults(textUserInterface,controller,gamestate);
    }

    @Test
    void testShouldShowResultsScene() {
        //prints YOU WON message
       results.drawResults();
       List<LocalLeaderBoard> leaderBoard = new ArrayList<>();
       leaderBoard.add(new LocalLeaderBoard(test2,true));
       leaderBoard.add(new LocalLeaderBoard(test1,false));
       gamestate.setLeaderboard(leaderBoard);
       //prints GAME ENDED
       results.drawResults();
    }


    @Test
    void testShouldDrawTribes(){
        //both players have 4 cards, white and black player.
        results.drawTribes();

    }

    @Test
    void testShouldDrawLeaderBoard(){
        List<GlobalRankingEntry> ranking = new ArrayList<>();
        GlobalRankingEntry e1 = new GlobalRankingEntry("0034",0034,34,34,34);
        GlobalRankingEntry e2 = new GlobalRankingEntry("FedePoci",100,25,20,2);
        GlobalRankingEntry e3 = new GlobalRankingEntry("Sinner",1000,2,100,1);
        //if someone has unrealistic values, it gets corrected
        GlobalRankingEntry cheater = new GlobalRankingEntry("CheaterWithVeryLongNameeeeeeee",1000000000,20000,1000000,1000000);

        ranking.add(e1);
        ranking.add(e2);
        ranking.add(e3);
        ranking.add(cheater);
        gamestate.setGlobalRanking(ranking);
        results.drawLeaderboard();
    }
}
