package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;
import it.polimi.ingsw.am31.am31.network.socket.client.SocketClient;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.tui.TextUserInterface;


import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Scanner;

public class Client {
    //main client class
    public static void main(String[] args) throws IOException, NotBoundException, TooManyPlayersException, Exception  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Nickname: ");
        String nickname = args[1];//scanner.nextLine();

        System.out.println("Enter server IP: ");
        //String ip = scanner.nextLine(); // aggiunto
        //FOR TESTING: uncomment this to use a fixed ip.
        String ip = "127.0.0.1";

        System.out.println("Enter 1 for RMI, 2 for Socket: ");
        int type = 1;  //scanner.nextInt();
        int newport = ClientConfig.CLIENT_PORT;
        VirtualServer connection = null;
        switch (type) {
            //ip is localhost
            //case 1 starts
            case 1: connection = new RmiClient(ip,newport,nickname);
                break;
            case 2: connection = new SocketClient(ip,ServerConfig.SERVER_PORT_SOCKET,nickname); //temporary
                break;
            default:
                break;
        }

        ClientController controller = new ClientController(connection);
        controller.ping();
        View view = null;
        System.out.println("Enter 1 for TUI, 2 for GUI");
        //i want the gameState to be shared by connection (for updates) and view (for visualization)
        LocalGameState gameState = new LocalGameState();
        ((RmiClient) connection).setGameState(gameState); //(not definitive)
        int viewType = 1; //scanner.nextInt();
        switch (viewType) {
            case 1: view = new TextUserInterface(controller, gameState);
            break;
          //  case 2: view = new GraphicUserInterface()
            //break;
            default:
                break;
        }
        gameState.addObserver((LocalObserver) view);
            view.Start();//after this, the clients acts through the view
    }

}
