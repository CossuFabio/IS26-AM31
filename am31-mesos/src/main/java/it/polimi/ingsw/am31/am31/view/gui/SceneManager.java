package it.polimi.ingsw.am31.am31.view.gui;

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
    private final VirtualServer server;
    private final LocalGameState localGameState;
    private BaseController currentController = null;

    public SceneManager (Stage stage, VirtualServer server, LocalGameState localGameState) {
        this.stage = stage;
        this.server = server;
        this.localGameState = localGameState;
    }
    
    //methods to switch the scene
    public void showLogin() {}
    public void showGame() {}
    public void showWaitingRoom() {}
    public void showEndGame() {}
    
    public void switchTo(String path, String title) {
        Platform.runLater( () -> {
            try {
                if (currentController != null)
                    localGameState.removeObserver(currentController);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent root = loader.load();

                BaseController controller = loader.getController();
                controller.setServer(server);
                controller.setLocalGameState(localGameState);
                controller.setSceneManager(this);
                localGameState.addObserver(controller);
                currentController = controller;

                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                System.err.println("Failed to load the FXML file " + path);
            }
        });
    }
}
