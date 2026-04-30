package it.polimi.ingsw.am31.am31.view.gui;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import javafx.application.Application;
import javafx.stage.Stage;

//classe che fa il setup iniziale e mostra la prima schermata, non gestisce eventi, non modifica la UI
public class GUIView extends Application {
    private static ClientController controller;
    private static LocalGameState localGameState;

    public static void setController (ClientController c) {
        controller = c;
    }

    public static void setLocalGameState (LocalGameState ls) {
        localGameState = ls;
    }
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = new SceneManager(stage, controller, localGameState);
        sceneManager.showLogin();
    }
}
