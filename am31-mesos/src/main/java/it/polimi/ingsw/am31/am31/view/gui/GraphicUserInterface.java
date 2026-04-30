package it.polimi.ingsw.am31.am31.view.gui;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.View;
import javafx.application.Application;

import java.util.List;

public class GraphicUserInterface implements View, LocalObserver {
    private final ClientController controller;
    private final LocalGameState localGameState;

    public GraphicUserInterface(ClientController controller, LocalGameState localGameState) {
        this.controller = controller;
        this.localGameState = localGameState;
    }

    @Override
    public void Start() throws Exception {
        GUIView.setController(controller);
        GUIView.setLocalGameState(localGameState);
        Application.launch(GUIView.class);
    }

    @Override
    public void printScreen() {}

    @Override
    public void onGameStartUpdate() {

    }

    @Override
    public void onRoundPhaseUpdate() {

    }

    @Override
    public void onRoundNumberUpdate() {

    }

    @Override
    public void onShowLobbyUpdate(List<LobbyDescriptor> lobbies) {

    }

    @Override
    public void onCardLineUpdate() {

    }

    @Override
    public void onPlayerListUpdate() {

    }

    @Override
    public void onEraUpdate() {

    }

    @Override
    public void onOfferTrackUpdate() {

    }

    @Override
    public void onPlayerScoreUpdate() {

    }

    @Override
    public void onPlayerTribeUpdate() {

    }

    @Override
    public void onTurnOrderUpdate() {

    }

    @Override
    public void onLobbyError(String errorMsg) {

    }

    @Override
    public void onSuccessRegistration(SuccessRegistrationUpdate msg) {

    }
}
