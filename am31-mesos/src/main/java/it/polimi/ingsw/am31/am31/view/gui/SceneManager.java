package it.polimi.ingsw.am31.am31.view.gui;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalBoardState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.gui.scene.BaseController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

//classe per gestire la navigazione delle scene.
public class SceneManager {
    private final Stage stage;
    private final ClientController controller;
    private final LocalGameState localGameState;
    private BaseController currentController = null;

    public SceneManager (Stage stage, ClientController controller, LocalGameState localGameState) {
        this.stage = stage;
        this.controller = controller;
        this.localGameState = localGameState;
    }

    //methods to switch the scene
    public void showLogin() {
        switchTo("/it/polimi/ingsw/am31/am31/view/gui/scene/login.fxml", "Login");
    }
    public void showGame() { }
    public void showWaitingRoom() {switchTo("/it/polimi/ingsw/am31/am31/view/gui/scene/waitingRoom.fxml", "WaitingRoom");}
    public void showEndGame() {}

    public void switchTo(String path, String title) {
        Platform.runLater( () -> {
            try {
                if (currentController != null)
                    localGameState.removeObserver(currentController);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();

                BaseController sceneController = loader.getController();
                sceneController.setController(controller);
                sceneController.setLocalGameState(localGameState);
                sceneController.setSceneManager(this);
                localGameState.addObserver(sceneController);
                currentController = sceneController;

                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                System.err.println("Failed to load the FXML file " + path);
            }
        });
    }
}
