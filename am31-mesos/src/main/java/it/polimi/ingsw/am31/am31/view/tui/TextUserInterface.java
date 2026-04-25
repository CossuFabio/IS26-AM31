package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.view.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TextUserInterface implements View, LocalObserver {
    private final ClientController controller;
    private LocalGameState gameState;
    private int currentPhase = 1; //TODO change into enum
    private TUIPhase phase;
    private List<Card> cards;
    private List<OfferCard> offercards;

    public TextUserInterface (ClientController controller, LocalGameState gameState){
        this.controller=controller;
        this.gameState = gameState;
        //gamestate starts as null
        this.phase = new TUIlobby(this, controller);
        cards = new ArrayList<Card>();
        offercards = new ArrayList<OfferCard>();
    }
//class for visualization via CLI
    @Override
    public void Start() throws Exception {
        //should create its own card dictionary / deck? just the list without deck functions
        //maybe a map like this? Map<String, Card> cards;
        GameResources gameResources = new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier());

        cards.addAll(gameResources.getTribeCards());
        cards.addAll(gameResources.getBuildingCards());
        offercards.addAll(gameResources.getOfferCards());
        //now cards has every possible card, we need to find cards using their id
        //we can use a mapper

        //draws the current phase
        while(true)
            TUIPhase.draw();
    }
    @Override
    public void PrintScreen() {
    }

    //Observer methods
    @Override
    public void onGameStartUpdate() {
        //this changes interface into game interface, no more join lobby, create lobby, etc...
        //implemented for tui.
        System.out.print("\033[H\033[2J");
        //currentPhase = ;
    }
    @Override
    public void onRoundNumberUpdate(){
        //tui handling the change
        //reprint everything? change the view phase? idk
    }
    @Override
    public void onRoundPhaseUpdate(){
        //tui handling the change

    }
    @Override
    public void onShowLobbyUpdate(List<LobbyDescriptor> lobbies){
    //tui shows the lobbies
    }
    @Override
    public void onCardLineUpdate() {
        //
    }
    @Override
    public void onPlayerListUpdate(){
//
    }

    @Override
    public void onEraUpdate() {
  //
    }
}
