package it.polimi.ingsw.am31.am31.view.gui;

import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import javafx.application.Application;
import javafx.stage.Stage;

//classe che fa il setup iniziale e mostra la prima schermata, non gestisce eventi, non modifica la UI
public class GUIView extends Application {
    private static VirtualServer server;
    private static LocalGameState localGameState;

    public static void setServer (VirtualServer s) {
        server = s;
    }

    public static void setLocalGameState (LocalGameState ls) {
        localGameState = ls;
    }
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = new SceneManager(stage, server, localGameState);
        sceneManager.showLogin();
    }
}
