package it.polimi.ingsw.am31.am31.fx;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEndedEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameStartingEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalLeaderBoard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.tui.TextUserInterface;

import java.util.List;

public class TUIEndGamePreview {

    public static void main(String[] args) throws Exception {
        ViewEventBus eventBus = new ViewEventBus();

        VirtualServer fakeServer = new VirtualServer() {
            @Override public void sendRequest(NetworkRequest r) {}
            @Override public void disconnect() {}
            @Override public void setIdentifier(String id) {}
        };

        ClientController fakeController = new ClientController(fakeServer, eventBus);
        fakeController.setLocalNameTest(); // username = "test"

        LocalPlayerState me    = new LocalPlayerState("test",  Color.RED);
        LocalPlayerState fabio = new LocalPlayerState("Fabio", Color.BLUE);
        LocalPlayerState mario = new LocalPlayerState("Mario", Color.YELLOW);
        me.setPrestigePoints(120);    me.setFood(500);
        fabio.setPrestigePoints(95);  fabio.setFood(1000);
        mario.setPrestigePoints(80);  mario.setFood(20);

        LocalGameState fakeState = new LocalGameState();
        TextUserInterface tui = new TextUserInterface(fakeController, fakeState, eventBus);

        // REGISTER → MAIN_MENU: successRegistration() calls gameState.reset() internally,
        // so the fake state must be populated AFTER this event.
        eventBus.post(new SuccessRegistrationEvent("test"));
        // MAIN_MENU → GAME
        eventBus.post(new GameStartingEvent());

        // populate after the reset
        fakeState.setPlayers(List.of(me, fabio, mario));
        fakeState.setTurnOrder(List.of(me, fabio, mario));
        fakeState.setRoundNumber(9);
        fakeState.setEra(3);
        fakeState.setLeaderboard(List.of(
                new LocalLeaderBoard(me,    true),
                new LocalLeaderBoard(fabio, false),
                new LocalLeaderBoard(mario, false)
        ));
        fakeState.setGlobalRanking(List.of(
                new GlobalRankingEntry("test",       800, 3000, 7,  1),
                new GlobalRankingEntry("Fabio",      750, 2000, 5,  2),
                new GlobalRankingEntry("Mario",      700, 1800, 6,  3),
                new GlobalRankingEntry("Luca",       680, 1750, 4,  4),
                new GlobalRankingEntry("Giulia",     660, 1600, 8,  5),
                new GlobalRankingEntry("Alessandro", 640, 1550, 3,  6),
                new GlobalRankingEntry("Sara",       620, 1400, 5,  7),
                new GlobalRankingEntry("Marco",      600, 1350, 9,  8),
                new GlobalRankingEntry("Chiara",     580, 1300, 4,  9),
                new GlobalRankingEntry("Davide",     560, 1250, 6,  10),
                new GlobalRankingEntry("Elena",      540, 1200, 3,  11),
                new GlobalRankingEntry("Matteo",     520, 1150, 7,  12),
                new GlobalRankingEntry("Anna",       500, 1100, 5,  13),
                new GlobalRankingEntry("Roberto",    480, 1050, 4,  14),
                new GlobalRankingEntry("Francesca",  460, 1000, 2,  15),
                new GlobalRankingEntry("Paolo",      440,  950, 6,  16),
                new GlobalRankingEntry("Valentina",  420,  900, 3,  17),
                new GlobalRankingEntry("Giorgio",    400,  850, 8,  18),
                new GlobalRankingEntry("Laura",      380,  800, 4,  19),
                new GlobalRankingEntry("Simone",     360,  750, 5,  20),
                new GlobalRankingEntry("Marta",      340,  700, 2,  21),
                new GlobalRankingEntry("Lorenzo",    320,  650, 7,  22),
                new GlobalRankingEntry("Alice",      300,  600, 3,  23),
                new GlobalRankingEntry("Riccardo",   280,  550, 4,  24),
                new GlobalRankingEntry("Beatrice",   260,  500, 6,  25),
                new GlobalRankingEntry("Stefano",    240,  450, 2,  26),
                new GlobalRankingEntry("Elisa",      220,  400, 5,  27),
                new GlobalRankingEntry("Nicola",     200,  350, 3,  28),
                new GlobalRankingEntry("Irene",      180,  300, 4,  29),
                new GlobalRankingEntry("Andrea",     160,  250, 1,  30)
        ));

        // GAME → RESULTS
        eventBus.post(new GameEndedEvent());

        // already in TUIResults phase — enter the interactive loop
        tui.startView();
    }
}
