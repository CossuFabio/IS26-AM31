package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;

import java.util.List;
import java.util.Scanner;

public class TextUserInterface implements View{

    private final ClientController controller;
    private LocalGameState gameState;
    private final IEventBus eventBus;


    private volatile TUIPhase currentphase;

    public TextUserInterface (ClientController controller, LocalGameState gameState, IEventBus eventBus){
        this.controller=controller;
        this.gameState = gameState;
        //gamestate starts as null
        this.currentphase = new TUIlobby(this, controller);
        this.eventBus = eventBus;

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

}
