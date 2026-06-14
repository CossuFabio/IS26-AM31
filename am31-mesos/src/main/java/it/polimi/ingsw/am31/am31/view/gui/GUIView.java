package it.polimi.ingsw.am31.am31.view.gui;


import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Class for the initial setup of the GUI.
 * Shows the first scene.
 */
public class GUIView extends Application {
    private static ClientController controller;
    private static LocalGameState localGameState;
    private static IEventBus eventBus;

    /**
     * Sets the client controller used to send actions to the server.
     * Must be called before launching the application.
     *
     * @param c the ClientController instance
     */
    public static void setController (ClientController c) {
        controller = c;
    }

    /**
     * Sets the local game state shared across all scene controllers.
     * Must be called before launching the application.
     *
     * @param ls the LocalGameState instance
     */
    public static void setLocalGameState (LocalGameState ls) {
        localGameState = ls;
    }

    /**
     * Sets the event bus used to communicate events between components.
     * Must be called before launching the application.
     *
     * @param e the IEventBus instance
     */
    public static void setEventBus (IEventBus e) {
        eventBus = e;
    }

    /**
     * Starts the GUI showing the login scene.
     * @param stage the primary JavaFX stage
     * @throws Exception if the login scene cannot be loaded
     */
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = new SceneManager(stage, controller, localGameState, eventBus);
        stage.setMaximized(true);
        stage.setMinWidth(javafx.stage.Screen.getPrimary().getBounds().getWidth());
        stage.setMinHeight(javafx.stage.Screen.getPrimary().getBounds().getHeight());

        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(PathConstants.ASSETS_PATH + "icon.png"))));

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
