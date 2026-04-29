package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.View;

import java.util.List;
import java.util.Scanner;

public class TextUserInterface implements View, LocalObserver {
    private final ClientController controller;
    private LocalGameState gameState;
    private volatile TUIPhase currentphase;
    private List<Card> cards;
    private List<OfferCard> offercards;

    public TextUserInterface (ClientController controller, LocalGameState gameState){
        this.controller=controller;
        this.gameState = gameState;
        //gamestate starts as null
        this.currentphase = new TUIlobby(this, controller);
    }
//class for visualization via CLI
    @Override
    public void Start() throws Exception {

        //draws the current phase
            printScreen();

        //we need an input thread
        Thread inputThread = new Thread(()-> {
           Scanner scanner = new Scanner(System.in);
           while(true){
               String input = scanner.nextLine();
               try {
                   currentphase.handleInput(input); //we don't send directly to the server, inputs
                   //change meaning depending on currentphase
               } catch (Exception e) {
                   System.err.println("error");
               }

           }
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    @Override
    public void printScreen() {
        System.out.println("\\033[H\\033[2J"); //doenst work
        System.out.flush();

            currentphase.draw();
    }


    public void changePhase(TUIPhase newphase){
        currentphase=newphase;
        printScreen();
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
        //could use update visitor for these methods
        lobbies.forEach(l -> {System.out.println("Partita: " + l.getId() + ", richiede: " + l.getnPlayers() + " giocatori. Giocatori in lobby: " + l.getFreeSlots());});
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
    @Override
    public void onPlayerTribeUpdate(){}

    @Override
    public void onTurnOrderUpdate(){}
}
