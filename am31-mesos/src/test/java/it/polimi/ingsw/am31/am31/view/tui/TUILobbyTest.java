package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.network.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;

import static org.junit.jupiter.api.Assertions.*;



class TUILobbyTest {
    TUILobby tuiLobby;

    @BeforeEach
    void setUp() {
        ViewEventBus eventBus = new ViewEventBus();
        ClientController cont = new ClientController(null, eventBus);

        tuiLobby = new TUILobby(null,cont);
    }

    @Test
    void TestDrawTUILobby() {
        tuiLobby.draw();

    }
}