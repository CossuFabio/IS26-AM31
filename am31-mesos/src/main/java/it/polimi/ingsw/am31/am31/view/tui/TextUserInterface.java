package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.View;

import java.util.List;

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
    }
//class for visualization via CLI
    @Override
    public void Start() throws Exception {

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

    @Override
    public void onOfferTrackUpdate(){
//
    }

    @Override
    public void onPlayerScoreUpdate(){
//
    }

}
