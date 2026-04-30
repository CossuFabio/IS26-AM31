package it.polimi.ingsw.am31.am31.view.gui.scene;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.gui.SceneManager;

//implementa LocalObserver perchè ogni controller deve reagire agli eventi per aggiornare la propria schermata
public abstract class BaseController implements LocalObserver {
    protected ClientController controller;
    protected LocalGameState localGameState;
    protected SceneManager sceneManager;

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void setLocalGameState(LocalGameState localGameState) {
        this.localGameState = localGameState;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
