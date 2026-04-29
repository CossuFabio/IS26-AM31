package it.polimi.ingsw.am31.am31.view.gui;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;
import javafx.application.Application;

public class GraphicUserInterface implements View {
    private final VirtualServer server;
    private final LocalGameState localGameState;

    public GraphicUserInterface(VirtualServer server, LocalGameState localGameState) {
        this.server = server;
        this.localGameState = localGameState;
    }

    @Override
    public void Start() throws Exception {
        GUIView.setServer(server);
        GUIView.setLocalGameState(localGameState);
        Application.launch(GUIView.class);
    }

    @Override
    public void printScreen() {}
}
