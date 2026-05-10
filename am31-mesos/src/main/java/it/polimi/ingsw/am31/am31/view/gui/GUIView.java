package it.polimi.ingsw.am31.am31.view.gui;


import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

//classe che fa il setup iniziale e mostra la prima schermata, non gestisce eventi, non modifica la UI
public class GUIView extends Application {
    private static ClientController controller;
    private static LocalGameState localGameState;
    private static IEventBus eventBus;

    public static void setController (ClientController c) {
        controller = c;
    }

    public static void setLocalGameState (LocalGameState ls) {
        localGameState = ls;
    }

    public static void setEventBus (IEventBus e) {
        eventBus = e;
    }

    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = new SceneManager(stage, controller, localGameState, eventBus);
        stage.setMaximized(true);
        stage.setMinWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        stage.setMinHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());


        //Callback invoked when pressing X button on the window
        stage.setOnCloseRequest(e -> {
            //We send the disconnection request, ignoring possible errors
            try {
                controller.disconnect();
            } catch (Exception ignored) {}

            //Kills the GUI
            Platform.exit();

            //Completely kill the Game process and all the related threads
            System.exit(0);
        });

        sceneManager.showLogin();
    }


}
