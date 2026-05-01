package it.polimi.ingsw.am31.am31.view.gui;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import javafx.application.Application;

import java.util.List;

public class GraphicUserInterface implements View{
    private final ClientController controller;
    private final LocalGameState localGameState;
    private final IEventBus eventBus;

    public GraphicUserInterface(ClientController controller, LocalGameState localGameState, IEventBus eventBus) {

        this.controller = controller;
        this.localGameState = localGameState;
        this.eventBus = eventBus;

    }

    @Override
    public void Start() throws Exception {
        GUIView.setController(controller);
        GUIView.setLocalGameState(localGameState);
        Application.launch(GUIView.class);
    }

    @Override
    public void printScreen() {}




}
