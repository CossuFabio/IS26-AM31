package it.polimi.ingsw.am31.am31.view.tui;

import com.google.common.eventbus.Subscribe;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameEndedEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.GameStartingEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class TextUserInterface implements View{

    private final ClientController controller;
    private final LocalGameState gameState;
    private final IEventBus eventBus;

    private volatile TUIPhase currentPhase;

    private enum Scene {REGISTER, MAIN_MENU, GAME}
    private Scene currentScene;

    public TextUserInterface (ClientController controller, LocalGameState gameState, IEventBus eventBus){

        this.controller=controller;
        this.gameState = gameState;
        this.eventBus = eventBus;
        this.currentScene = Scene.REGISTER;

        this.currentPhase = new TUIRegistration(this, controller);
        eventBus.register(currentPhase);
        eventBus.register(this);

    }

    //class for visualization via CLI
    @Override
    public void startView() throws Exception {
        printScreen();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {       // Closes the thread when CLI is closed.
            String input = scanner.nextLine();
            try {
                currentPhase.handleInput(input);
            } catch (Exception e) {
                System.err.println("error: " + e.getMessage());
            }
        }
    }

    public void printScreen() {
        System.out.println("\n----------------------------------------\n");
        currentPhase.draw();
    }

    private void changePhase(TUIPhase newPhase){
        eventBus.unregister(currentPhase);
        currentPhase = newPhase;
        eventBus.register(currentPhase);
        printScreen();
    }

    @Subscribe
    public void gameStarting(GameStartingEvent e){
        if(currentScene == Scene.MAIN_MENU){
            this.currentScene = Scene.GAME;
            changePhase(new TUIGamePhase(this, controller, gameState));
        }
    }

    @Subscribe
    public void gameEnded(GameEndedEvent e){
        if(currentScene == Scene.GAME){
            this.currentScene = Scene.MAIN_MENU;
            gameState.reset();
            changePhase(new TUILobby(this, controller));
        }
    }




    @Subscribe
    public void successRegistration(SuccessRegistrationEvent e){
        if(currentScene == Scene.REGISTER){
            System.out.println("Registered successfully with username " + e.getIdentifier());
            this.currentScene = Scene.MAIN_MENU;
            gameState.reset();
            changePhase(new TUILobby(this, controller));
        }
    }


}
