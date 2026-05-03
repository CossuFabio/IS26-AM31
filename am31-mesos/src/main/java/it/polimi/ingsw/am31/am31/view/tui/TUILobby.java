package it.polimi.ingsw.am31.am31.view.tui;

import com.google.common.eventbus.Subscribe;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ClientController;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.FailedRegistrationEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.InvalidColorPickEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ShowLobbyEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;

public class TUILobby implements TUIPhase {

    private final TextUserInterface TUI;
    private final ClientController controller;
    private boolean creatingGame;
    private Color color;
    private int gameId;


    private enum TuiLobbyStep {START,
        WAIT_LOBBIES,
        CREATING_GAME,
        SELECT_COLOR,
        JOINING_GAME, WAIT_JOIN_GAME_RESPONSE,
        WAITING_GAMESTART}

    private volatile TuiLobbyStep currentStep;

    private int nPlayers;

    public TUILobby(TextUserInterface TUI, ClientController controller) {
        this.TUI = TUI;
        this.controller = controller;
        this.currentStep=TuiLobbyStep.START;
    }

    @Override
    public void draw() {
            switch (currentStep) {
                case START: {
                    creatingGame = false;
                    System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby\n");
                    break;
                }
                case CREATING_GAME:{
                    System.out.println("Enter the number of players (2-5)\n");
                    creatingGame = true;
                    break;
                }
                case SELECT_COLOR:{
                    System.out.println("Choose your totem's color (white, black, red, yellow, blue)\n");
                    break;
                }
                case JOINING_GAME:{
                    System.out.println("Choose a gameId\n");
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
        if (input == null || input.isEmpty())
            return;
        switch(currentStep){
            case START:{
                switch(input){
                    case "1": {currentStep=TuiLobbyStep.CREATING_GAME;}
                        TUI.printScreen();
                        break;
                    case "2": controller.sendRequest(new ShowLobbyNetworkRequest());
                        break;
                    case "3":{currentStep=TuiLobbyStep.JOINING_GAME;}
                        TUI.printScreen();
                        break;
                    default:
                        System.out.println("Invalid input!");
                        TUI.printScreen();
                        break;
            }
            break;
            }
            case CREATING_GAME:{

                try{
                    nPlayers = Integer.parseInt(input);
                }catch(Exception e){
                    //sets invalid
                    nPlayers = -1;
                }


                if (nPlayers < 2 || nPlayers > 5) {System.out.println("Invalid number!\n");
                    break;
                }
                currentStep = TuiLobbyStep.SELECT_COLOR;
                TUI.printScreen();
                break;

            }
            case JOINING_GAME:{
                gameId = Integer.parseInt(input);
                //might throw numberformatexception
                currentStep= TuiLobbyStep.SELECT_COLOR;
                TUI.printScreen();
                break;
            }
            case SELECT_COLOR:{
                try {
                    color = Color.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid color\n");
                    TUI.printScreen();
                    break;
                }
                if(creatingGame) {
                    currentStep=TuiLobbyStep.WAITING_GAMESTART;
                    controller.sendRequest(new NewGameNetworkRequest(nPlayers, color));
                    creatingGame = false;
                    //this doesn't check if the request fails
                }
                else {
                    currentStep = TuiLobbyStep.WAITING_GAMESTART;
                    controller.sendRequest(new JoinGameNetworkRequest(color, gameId));
                    //this doesn't check if the request fails
                    }
                }
                TUI.printScreen();
                break;
            case WAITING_GAMESTART:{
                //ignores inputs? or smth

            }

        }


    }


    @Subscribe
    public void printLobbies(ShowLobbyEvent e){
        e.getLobbies().forEach(l -> System.out.println("Lobby: " + l.getId() + ", Num Giocatori:" + l.getFreeSlots() + "/" + l.getnPlayers()));
        TUI.printScreen();
    }

    @Subscribe
    public void invalidColorSelected(InvalidColorPickEvent e){
        System.out.println("Invalid color pick");
    }


}
