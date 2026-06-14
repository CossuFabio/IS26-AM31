package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ConnectionLostEvent;
import it.polimi.ingsw.am31.am31.view.gui.SceneManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * Abstract base class for all scene controllers.
 */
public abstract class BaseController {
    protected ClientController controller;
    protected LocalGameState localGameState;
    protected SceneManager sceneManager;
    protected IEventBus eventBus;

    /**
     * Sets the client controller used to send actions to the server.
     *
     * @param controller the ClientController instance
     */
    public void setController(ClientController controller) {
        this.controller = controller;
    }

    /**
     * Sets the local game state shared across all scene controllers.
     *
     * @param localGameState the LocalGameState instance
     */
    public void setLocalGameState(LocalGameState localGameState) {
        this.localGameState = localGameState;
    }

    /**
     * Sets the scene manager.
     * @param sceneManager the SceneManager instance
     */
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    /**
     * Sets the event bus used to communicate events between components.
     *
     * @param eventBus the IEventBus instance
     */
    public void setEventBus(IEventBus eventBus) { this.eventBus = eventBus; }

    /**
     * Handles a {@link ConnectionLostEvent} by showing an error alert and exiting the application.
     *
     * @param event the connection lost event
     */
    @Subscribe
    public void onConnectionLost(ConnectionLostEvent event) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Lost");
            alert.setHeaderText(null);
            alert.setContentText("Connection to the server was lost.");
            alert.showAndWait();
            Platform.exit();
        });
    }
}
