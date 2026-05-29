package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ConnectionLostEvent;
import it.polimi.ingsw.am31.am31.view.gui.SceneManager;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public abstract class BaseController {
    protected ClientController controller;
    protected LocalGameState localGameState;
    protected SceneManager sceneManager;
    protected IEventBus eventBus;

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void setLocalGameState(LocalGameState localGameState) {
        this.localGameState = localGameState;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void setEventBus(IEventBus eventBus) { this.eventBus = eventBus; }

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
