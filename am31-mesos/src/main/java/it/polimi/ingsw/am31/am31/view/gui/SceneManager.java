package it.polimi.ingsw.am31.am31.view.gui;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.gui.scene.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class to manage scene navigation.
 */
public class SceneManager {
    private final Stage stage;
    private final ClientController controller;
    private final LocalGameState localGameState;
    private final IEventBus eventBus;
    private BaseController currentController = null;

    /**
     * Creates a new SceneManager.
     * @param stage the primary JavaFX stage
     * @param controller the client controller for server communication
     * @param localGameState the shared local game state
     * @param eventBus the event bus for inter-component communication
     */
    public SceneManager (Stage stage, ClientController controller, LocalGameState localGameState, IEventBus eventBus) {
        this.stage = stage;
        this.controller = controller;
        this.localGameState = localGameState;
        this.eventBus = eventBus;
    }

    /**
     * Shows the login scene.
     */
    public void showLogin() {
        switchTo("/it/polimi/ingsw/am31/am31/view/gui/scene/login.fxml", "Login");
    }

    /**
     * Shows the game scene.
     */
    public void showGame() { switchTo("/it/polimi/ingsw/am31/am31/view/gui/scene/game.fxml", "Game"); }

    /**
     * Shows the waiting room scene.
     */
    public void showWaitingRoom() {switchTo("/it/polimi/ingsw/am31/am31/view/gui/scene/waitingRoom.fxml", "WaitingRoom");}

    /**
     * Shows the end game scene.
     */
    public void showEndGame() { switchTo("/it/polimi/ingsw/am31/am31/view/gui/scene/endGame.fxml", "EndGame");}

    /**
     * Changes the scene to the one indicated by the path.
     * @param path the FXML resource path of the scene to load
     * @param title the title to display on the window
     */
    public void switchTo(String path, String title) {
        Platform.runLater( () -> {
            try {
                if (currentController != null)

                    eventBus.unregister(currentController);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();

                BaseController sceneController = loader.getController();
                sceneController.setController(controller);
                sceneController.setLocalGameState(localGameState);
                sceneController.setSceneManager(this);
                sceneController.setEventBus(eventBus);

                eventBus.register(sceneController);
                currentController = sceneController;

                stage.setTitle(title);
                if (stage.getScene() == null) {
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    stage.getScene().setRoot(root);
                }

            } catch (IOException e) {
                System.err.println("Failed to load the FXML file " + path);
                e.printStackTrace();
            }
        });
    }
}
