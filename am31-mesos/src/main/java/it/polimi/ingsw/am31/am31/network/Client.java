package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;
import it.polimi.ingsw.am31.am31.network.socket.client.SocketClient;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.LocalState.StateErrorUpdater;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.gui.GraphicUserInterface;
import it.polimi.ingsw.am31.am31.view.tui.TextUserInterface;
import org.fusesource.jansi.AnsiConsole;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Client {
    //main client class
    public static void main(String[] args) throws IOException, NotBoundException, TooManyPlayersException, Exception  {

        //TODO: ADD CHECK FOR CORRECT COMMAND LINE PARAMS

        String connectionType = "1"; //args[0];
        String viewType = "2"; //args[1];

        //library to display colors, idk if this works, idk if this goes here
        AnsiConsole.systemInstall();

        LocalGameState gameState = new LocalGameState();
        StateUpdater stateUpdater = new StateUpdater(gameState);
        StateErrorUpdater errorUpdater = new StateErrorUpdater(gameState);
        MessageDispatcher messageDispatcher = new MessageDispatcher(stateUpdater, errorUpdater);

        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter Nickname: ");
        //String nickname = scanner.nextLine();


        VirtualServer connection = null;
        switch (connectionType) {
            case "1": connection = new RmiClient(ServerConfig.SERVER_IP_ADDRESS, ClientConfig.CLIENT_PORT, messageDispatcher);
                break;
            case "2": connection = new SocketClient(ServerConfig.SERVER_IP_ADDRESS, ServerConfig.SERVER_PORT_SOCKET, messageDispatcher); //temporary
                break;
            default:
                break;
        }



        ClientController controller = new ClientController(connection);
        View view = null;


        //connection.sendRequest(new NewGameNetworkRequest(3, Color.RED));
        switch (viewType) {
            case "1": view = new TextUserInterface(controller, gameState);
            break;
            case "2": view = new GraphicUserInterface(controller, gameState);
            break;
            default:
                break;
        }
        //move to view?
        gameState.addObserver((LocalObserver) view);
            view.Start();//after this, the clients acts through the view

    }

}
