package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.view.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;

import java.util.Scanner;

public class TextUserInterface implements View, ObserverHandler {
    private final ClientController controller;
    private LocalGameState gameState;
    private int currentPhase = 1; //TODO change into enum
    private TUIPhase phase;

    public TextUserInterface (ClientController controller, LocalGameState gameState){
        this.controller=controller;
        this.gameState = gameState;
        //gamestate starts as null
        this.phase = new TUIlobby(this, controller);
    }
//class for visualization via CLI
    @Override
    public void Start() throws Exception {
            TUIPhase.draw();
    }

    @Override
    public void PrintScreen() {


    }

    @Override
    public void onPlayerNewBuildingEvent(Player player) {

    }

    @Override
    public void onPlayerScoresUpdate(Player player) {

    }

    @Override
    public void onPlayerTribeUpdate(Player player) {

    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {

    }

    @Override
    public void onPlayersListUpdate(Game game) {

    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {

    }

    @Override
    public void onOfferTrackUpdate(Board board) {

    }

    @Override
    public void onTurnOrderUpdate(Board board) {

    }

    @Override
    public void onGameStartUpdate() {
        //this.GameStart(), changes interface into game interface, no more join lobby, create lobby, etc...
        //implemented for tui.
        System.out.print("\033[H\033[2J");
        //currentPhase = ;
    }

    @Override
    public void addObserver(GameObserver o) {

    }

    @Override
    public void removeObserver(GameObserver o) {

    }
}
