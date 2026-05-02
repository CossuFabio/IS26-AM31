package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;
import it.polimi.ingsw.am31.am31.network.socket.client.SocketClient;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.StateErrorUpdater;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import it.polimi.ingsw.am31.am31.view.gui.GraphicUserInterface;
import it.polimi.ingsw.am31.am31.view.tui.TextUserInterface;
import org.fusesource.jansi.AnsiConsole;

public class Client {
    //main client class
    public static void main(String[] args){

        //TODO: ADD CHECK FOR CORRECT COMMAND LINE PARAMS

        String connectionType = "1"; //args[0];
        String viewType = "1"; //args[1];

        //library to display colors, idk if this works, idk if this goes here
        AnsiConsole.systemInstall();

        //Wiring all necessary objects
        IEventBus eventBus = new ViewEventBus();
        LocalGameState gameState = new LocalGameState();
        StateUpdater stateUpdater = new StateUpdater(gameState, eventBus);
        StateErrorUpdater errorUpdater = new StateErrorUpdater(gameState, eventBus);
        MessageDispatcher messageDispatcher = new MessageDispatcher(stateUpdater, errorUpdater);


        VirtualServer connection = null;
        try{
            switch (connectionType) {
                case "1": connection = new RmiClient(ServerConfig.SERVER_IP_ADDRESS, ClientConfig.CLIENT_PORT, messageDispatcher);
                    break;
                case "2": connection = new SocketClient(ServerConfig.SERVER_IP_ADDRESS, ServerConfig.SERVER_PORT_SOCKET, messageDispatcher); //temporary
                    break;
                default:
                    break;
            }
        }catch(Exception e){
            System.out.println("Unable to connect to the server");
            return;
        }


        ClientController controller = new ClientController(connection, eventBus);
        View view = null;


        switch (viewType) {
            case "1": view = new TextUserInterface(controller, gameState, eventBus);
            break;
            case "2": view = new GraphicUserInterface(controller, gameState, eventBus);
            break;
            default:
                break;
        }
        //move to view?
        try{
            view.startView();//after this, the clients acts through the view
        }catch(Exception e){
            System.out.println("An error with the requested view occurred");
        }

    }

}
