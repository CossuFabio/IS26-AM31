package it.polimi.ingsw.am31.am31.view.tui;

import com.google.common.eventbus.Subscribe;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedJoinLobby;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.InvalidColorPickEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.PlayersInLobbyChangedEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ShowLobbyEvent;

public class TUILobby implements TUIPhase {

    private final TextUserInterface TUI;
    private final ClientController controller;
    private boolean creatingGame;
    private Color color;
    private int gameId;


    private enum TuiLobbyStep {
        START,
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
                creatingGame = false;
                System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby\n");
                break;
            }
            case CREATING_GAME: {
                System.out.println("Enter the number of players (2-5) " + TUIConfig.GO_BACK_STRING + "\n");
                creatingGame = true;
                break;
            }
            case SELECT_COLOR: {
                System.out.println("Choose your totem's color (white, black, red, yellow, blue) " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case JOINING_GAME: {
                creatingGame = false;
                System.out.println("Choose a gameId " + TUIConfig.GO_BACK_STRING + "\n");
                break;
            }
            case WAITING_GAMESTART: {
                System.out.println("Waiting for the game to Start...\n");
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
                        currentStep = TuiLobbyStep.CREATING_GAME;
                        TUI.printScreen();
                        break;
                    }

                    case "2": {
                        controller.sendRequest(new ShowLobbyNetworkRequest());
                        break;
                    }
                    case "3": {
                        currentStep = TuiLobbyStep.JOINING_GAME;
                        TUI.printScreen();
                        break;
                    }
                    default:
                        System.out.println("Invalid input!");
                        TUI.printScreen();
                        break;
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
                    System.out.println("Invalid number!\n");
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
                    System.out.println("Invalid input");
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
                    System.out.println("Invalid color\n");
                    TUI.printScreen();
                    break;
                }
                if (creatingGame) {
                    currentStep = TuiLobbyStep.WAITING_GAMESTART;
                    controller.sendRequest(new NewGameNetworkRequest(nPlayers, color));
                    creatingGame = false;

                } else {
                    currentStep = TuiLobbyStep.WAITING_GAMESTART;
                    controller.sendRequest(new JoinGameNetworkRequest(color, gameId));

                }
            }
            TUI.printScreen();
            break;
            case WAITING_GAMESTART: {
                break;
            }

        }


    }


    @Subscribe
    public void printLobbies(ShowLobbyEvent e) {
        e.getLobbies().forEach(l -> System.out.println("Lobby: " + l.getId() + ", Free slots: " + l.getFreeSlots() + ", Game for " + l.getnPlayers() + " players"));
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
        if (currentStep != TuiLobbyStep.WAITING_GAMESTART) {
            System.out.println("Players in lobby changed. New list:\n");
            e.getPlayers().forEach(p -> System.out.println("Nickname: " + p.getNickname() + ", color: " + p.getColor()));
            TUI.printScreen();
        }
    }

    @Subscribe
    public void unableToJoin(FailedJoinLobby e) {
        System.out.println("Unable to enter Lobby. " + e.getMessage());
    }

}