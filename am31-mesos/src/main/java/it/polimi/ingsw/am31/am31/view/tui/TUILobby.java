package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedJoinLobby;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.InvalidColorPickEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.PlayersInLobbyChangedEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ShowLobbyEvent;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class TUILobby implements TUIPhase {

    private final TextUserInterface TUI;
    private final ClientController controller;
    private boolean creatingGame;
    private Color color;
    private int gameId;


    private enum TuiLobbyStep {
        START,
        RULES_EXPLANATION,
        CREATING_GAME,
        SELECT_COLOR,
        JOINING_GAME,
        WAITING_GAMESTART
    }

    private volatile TuiLobbyStep currentStep;

    private int nPlayers;

    public TUILobby(TextUserInterface TUI, ClientController controller) {
        this.TUI = TUI;
        this.controller = controller;
        this.currentStep = TuiLobbyStep.START;
    }

    @Override
    public void draw() {
        switch (currentStep) {
            case START: {
                String spaces = String.format("%18s", " ");
                System.out.println("|"+spaces+"MESOS  LOBBY"+spaces+"|");
                System.out.println("--------------------------------------------------");
                System.out.println("\nPlease type:");
                System.out.println("["+ansi().fg(Ansi.Color.GREEN).a("1").reset() +" - To create a game]\n["+ansi().fg(Ansi.Color.GREEN).a("2").reset() +" - To show the current lobbies]\n["+ansi().fg(Ansi.Color.GREEN).a("3").reset()+" - To join a lobby]\n["+ansi().fg(Ansi.Color.GREEN).a("4").reset()+" - To read the game's rules]\n");
                break;
            }
            case RULES_EXPLANATION: {
                //TODO: Write this
                System.out.println(TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case CREATING_GAME: {
                System.out.println("\nEnter the number of players (2-5) " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case SELECT_COLOR: {
                System.out.println("\nChoose your totem's color (white, black, red, yellow, blue) " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case JOINING_GAME: {
                System.out.println("\nChoose a gameId " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case WAITING_GAMESTART: {
                System.out.println("\nWaiting for the game to Start...\n");
                break;
            }
        }
    }

    @Override
    public void handleInput(String input) throws Exception {
        if (input == null || input.isBlank())
            return;
        switch (currentStep) {
            case START: {
                switch (input) {
                    case "1": {
                        creatingGame = true;
                        currentStep = TuiLobbyStep.CREATING_GAME;
                        TUI.printScreen();
                        break;
                    }
                    case "2": {
                        controller.sendRequest(new ShowLobbyNetworkRequest());
                        break;
                    }
                    case "3": {
                        creatingGame = false;
                        currentStep = TuiLobbyStep.JOINING_GAME;
                        TUI.printScreen();
                        break;
                    }
                    case "4": {
                        currentStep = TuiLobbyStep.RULES_EXPLANATION;
                        TUI.printScreen();
                        break;
                    }
                    default: {
                        System.out.println("Invalid input!");
                        TUI.printScreen();
                        break;
                    }
                }
                break;
            }
            case CREATING_GAME: {

                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = TuiLobbyStep.START;
                    TUI.printScreen();
                    break;
                }

                try {
                    nPlayers = Integer.parseInt(input);
                } catch (Exception e) {
                    //sets invalid
                    nPlayers = 0;
                }

                if (nPlayers < GameConstants.MIN_PLAYERS || nPlayers > GameConstants.MAX_PLAYERS) {
                    System.out.println("\nInvalid number!\n");
                    TUI.printScreen();
                    break;
                }
                currentStep = TuiLobbyStep.SELECT_COLOR;
                TUI.printScreen();
                break;

            }
            case JOINING_GAME: {

                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = TuiLobbyStep.START;
                    TUI.printScreen();
                    break;
                }

                try {
                    gameId = Integer.parseInt(input);
                } catch (Exception e) {
                    System.out.println("\nInvalid input\n");
                    TUI.printScreen();
                    break;
                }

                currentStep = TuiLobbyStep.SELECT_COLOR;
                TUI.printScreen();
                break;
            }
            case SELECT_COLOR: {

                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = creatingGame ? TuiLobbyStep.CREATING_GAME : TuiLobbyStep.JOINING_GAME;
                    TUI.printScreen();
                    break;
                }


                try {
                    color = Color.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("\nInvalid color\n");
                    TUI.printScreen();
                    break;
                }
                currentStep = TuiLobbyStep.WAITING_GAMESTART;
                if (creatingGame) {
                    controller.sendRequest(new NewGameNetworkRequest(nPlayers, color));
                } else {
                    controller.sendRequest(new JoinGameNetworkRequest(color, gameId));
                }

                TUI.printScreen();
                break;
            }
            case RULES_EXPLANATION: {
                if (input.equals(TUIConfig.GO_BACK_VALUE)) {
                    currentStep = TuiLobbyStep.START;
                    TUI.printScreen();
                    break;
                }
                else {
                    System.out.println("\nInvalid input\n");
                    TUI.printScreen();
                    break;
                }
            }
            case WAITING_GAMESTART: {
                break;
            }

        }
    }


    @Subscribe
    public void printLobbies(ShowLobbyEvent e) {
        if(e.getLobbies().isEmpty()){
            System.out.println("\nNo lobbies available!\n");
            TUI.printScreen();
            return;
        }
        System.out.println("\nLobbies available:\n");
        e.getLobbies().forEach(
                l -> {
                    System.out.println("Lobby: " + l.getId() + ", Free slots: " + l.getFreeSlots() +
                            ", Game for " + l.getnPlayers() + " players.");
                    System.out.print("Available colors:");
                    l.getAvailableColors().forEach( c->{
                        System.out.print(" "+c);
                    });
                    System.out.println("\n");
                });
        TUI.printScreen();
    }

    @Subscribe
    public void invalidColorSelected(InvalidColorPickEvent e) {
        if (currentStep == TuiLobbyStep.WAITING_GAMESTART) {
            currentStep = TuiLobbyStep.SELECT_COLOR;
            System.out.println("Invalid color pick");
            TUI.printScreen();
        }

    }

    @Subscribe
    public void playersChanged(PlayersInLobbyChangedEvent e) {
        if (currentStep == TuiLobbyStep.WAITING_GAMESTART) {
            System.out.println("Players in lobby changed. New list:\n");
            e.getPlayers().forEach(p -> System.out.println(ansi().fg(Ansi.Color.YELLOW).a("Nickname: ").reset() + p.getNickname() + ", color: " + p.getColor()+"\n"));
            TUI.printScreen();
        }
    }

    @Subscribe
    public void unableToJoin(FailedJoinLobby e) {
        if (currentStep == TuiLobbyStep.WAITING_GAMESTART) {
            System.out.println("Unable to enter Lobby. " + e.getMessage());
            currentStep = TuiLobbyStep.START;
            TUI.printScreen();
        }
    }

}