package it.polimi.ingsw.am31.am31.view.tui;


import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.*;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Orchestrator for TUI scenes.
 */
public class TextUserInterface implements View{

    private final ClientController controller;
    private final LocalGameState gameState;
    private final IEventBus eventBus;

    private volatile TUIPhase currentPhase;

    private enum Scene {REGISTER, MAIN_MENU, GAME, RESULTS}
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
    /**
     * Prints the current screen, sets up input scanner to receive and handle player input
     */
    public void startView() throws Exception {
        printScreen();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {       // Closes the thread when CLI is closed.
            String input = scanner.nextLine();
            try {
                System.out.println();
                currentPhase.handleInput(input);
            } catch (Exception e) {
                System.err.println("error: " + e.getMessage());
            }
        }
        try {
            controller.disconnect();
        }catch (Exception ignored) {
            //We do not care if the disconnection doesn't succeed, the server will handle with at most
            //15 seconds of delay
        }
    }

    /**
     * Prints the current GamePhase. called everytime there's an update
     */
    public synchronized void printScreen() {
        System.out.println("--------------------------------------------------");
        currentPhase.draw();
    }

    /**
     * Changes the phase of the TUI screen, registers it to the eventBus
     * @param newPhase New Phase to be set
     */
    private void changePhase(TUIPhase newPhase){
        eventBus.unregister(currentPhase);
        currentPhase = newPhase;
        eventBus.register(currentPhase);
        printScreen();
    }

    @Subscribe
    /**
     * TUI subscribes to the GameStartingEvent, when it happens the game switches into the main GamePhase
     */
    public void gameStarting(GameStartingEvent e){
        if(currentScene == Scene.MAIN_MENU){
            this.currentScene = Scene.GAME;
            changePhase(new TUIGamePhase(this, controller, gameState));
        }
    }

    @Subscribe
    /**
     * TUI subscribes to The GameEndedEvent, when it happens the game switches into Results screen.
     */
    public void gameEnded(GameEndedEvent e) {
        if(currentScene == Scene.GAME) {
            this.currentScene = Scene.RESULTS;
            //GameState does not need to be reset
            changePhase(new TUIResults(this, controller, gameState));
        }
    }




    @Subscribe
    /**
     * TUI subscribes to SuccessRegistration, when it happens the player is registered on the server
     * and switches to the Lobby Screen
     */
    public void successRegistration(SuccessRegistrationEvent e){
        if(currentScene == Scene.REGISTER){
            System.out.println("\nRegistered successfully with username " + ansi().fg(Ansi.Color.GREEN).a(e.getIdentifier()).reset()+ "\n");
            this.currentScene = Scene.MAIN_MENU;
            gameState.reset();
            changePhase(new TUILobby(this, controller));
        }
    }

    @Subscribe
    /**
     * When a player leaves, server sends a GameCrashedEvent, when it happens the User is sent back to Lobby
     */
    public void gameCrashed(GameCrashedEvent e){
        if(currentScene == Scene.GAME){
            System.out.println("\nGame crashed! Returning to main menu");
            this.currentScene = Scene.MAIN_MENU;
            gameState.reset();
            changePhase(new TUILobby(this, controller));
        }
    }

    @Subscribe
    /**
     * TUI subscribes to ReturnLobbyEvent, posted by the tui itself when looking at results.
     * allows to go back to lobby after a game.
     */
    public void returnToLobby(ReturnToLobbyEvent e){
        if(currentScene == Scene.RESULTS){
            this.currentScene = Scene.MAIN_MENU;
            gameState.reset();
            changePhase(new TUILobby(this, controller));
        }
    }

    @Subscribe
    /**
     * TUI subscribes to ConnectionLostEvent, sent by server when its closed / the connection is severed
     * closes the program.
     */
    public void connectionLost(ConnectionLostEvent e){
        System.out.println("Lost connection with server!");
        System.exit(0);
    }

}
