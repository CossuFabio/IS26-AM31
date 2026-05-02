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
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;

public class TUILobby implements TUIPhase {
    private final TextUserInterface TUI;
    private final ClientController controller;
    private boolean creatingGame;
    private Color color;
    private int gameId;
    private IEventBus eventBus;

    private enum TuiLobbyStep {REGISTRATION, WAIT_REGISTRATION_RESULT, START,CREATING_GAME,SELECT_COLOR, JOINING_GAME, WAITING_GAMESTART}
    private volatile TuiLobbyStep currentstep;
    private int nplayers;

    public TUILobby(TextUserInterface TUI, ClientController controller, IEventBus eventBus) {
        this.TUI = TUI;
        this.controller = controller;
        this.currentstep=TuiLobbyStep.REGISTRATION;
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }
    @Override
    public void draw() {
            switch (currentstep) {
                case REGISTRATION:
                    System.out.println("Enter nickname:");break;
                case WAIT_REGISTRATION_RESULT:
                    break;
                case START:
                    creatingGame = false;
                    System.out.println("Type:\n1 - Create a game\n2 - Show the current lobbies\n3 - Join a lobby\n");break;
                case CREATING_GAME:{
                    System.out.println("Enter the number of players (2-5)\n");
                    creatingGame = true;break;
                }
                case SELECT_COLOR:{
                    System.out.println("Choose your totem's color (white, black, red, yellow, blue)\n");break;
                }
                case JOINING_GAME:{
                    System.out.println("Choose a gameId\n");break;
                }
                case WAITING_GAMESTART:
                    System.out.println("Waiting for the game to Start...\n");break;
            }
    }

    @Override
    public void handleInput(String input) throws Exception {
        if (input == null || input.equals(""))
            return;
        switch(currentstep){
            case REGISTRATION:
                String nickname = input.toString();
                controller.sendRequest(new NewServerConnectionRequest(nickname));
                currentstep = TuiLobbyStep.WAIT_REGISTRATION_RESULT;
                break;
            case WAIT_REGISTRATION_RESULT:
                break;
            case START:{
                switch(input){
                    case "1":{currentstep=TuiLobbyStep.CREATING_GAME;}break;
                    case "2":controller.sendRequest(new ShowLobbyNetworkRequest());break;
                    case "3":{currentstep=TuiLobbyStep.JOINING_GAME;}break;
                    default: System.out.println("Invalid input!");break;
            }
        break;}
            case CREATING_GAME:{
                nplayers = Integer.parseInt(input);
                //might throw numberformatexception
                if (nplayers < 2 || nplayers > 5) {System.out.println("Invalid number!\n"); break;}
                currentstep = TuiLobbyStep.SELECT_COLOR;break;
            }
            case JOINING_GAME:{
                gameId = Integer.parseInt(input);
                //might throw numberformatexception
                currentstep= TuiLobbyStep.SELECT_COLOR;break;
            }
            case SELECT_COLOR:{
                try {
                    color = Color.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid color\n");
                }
                if(creatingGame) {
                    controller.sendRequest(new NewGameNetworkRequest(nplayers, color));
                    creatingGame = false;
                    //this doesn't check if the request fails
                    currentstep=TuiLobbyStep.WAITING_GAMESTART;
                }
                else {
                    controller.sendRequest(new JoinGameNetworkRequest(color, gameId));
                    //this doesn't check if the request fails
                    currentstep = TuiLobbyStep.WAITING_GAMESTART;
                }
                }break;
            case WAITING_GAMESTART:{
                //ignores inputs? or smth

            }

        }
        TUI.printScreen();

    }
    public void handleError(String errorMsg){
        System.out.println("Received this msg: "+errorMsg+"\nGoing back to start\n");
        currentstep = TuiLobbyStep.START;
        TUI.printScreen();
    }

    @Subscribe
    public void successRegistration(SuccessRegistrationEvent e){
        if(currentstep == TuiLobbyStep.WAIT_REGISTRATION_RESULT){
            System.out.println("Registered successfully with username " + e.getIdentifier());
            currentstep = TuiLobbyStep.START;
            TUI.printScreen();
        }
    }

    @Subscribe
    public void failedRegistration(FailedRegistrationEvent e){
        if(currentstep == TuiLobbyStep.WAIT_REGISTRATION_RESULT){
            System.out.println("Failed registration...");
            currentstep = TuiLobbyStep.REGISTRATION;
            TUI.printScreen();
        }
    }


}
