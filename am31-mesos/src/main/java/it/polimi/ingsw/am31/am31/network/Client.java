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

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    //main client class
    public static void main(String[] args){


        //Uncomment this when submitting project

        /*final String RMI_STRING = "RMI";
        final String SOCKET_STRING = "SOCKET";

        final String TUI_STRING = "TUI";
        final String GUI_STRING = "GUI";

        if(args.length != 2 ){
            System.out.println("Invalid arguments!");
            System.out.println("First argument: connection type. Type " + RMI_STRING + " or " + SOCKET_STRING);
            System.out.println("Second argument: view type. Type " + TUI_STRING + " or " + GUI_STRING);
            return;
        }*/


        //Remove when project is finished and remember to change switch cases values to parametric strings
        String connectionType = "2"; //args[0];
        String viewType = "2"; //args[1];


        //library to display colors idk if this goes here
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
                case "1":
                    //This method allows us to discover the Client ip. It is needed to export the stub correctly
                    //It is not needed with Socket because there the IP is implicit in the connection Server-side
                    //WARNING: It works only in LAN. Fails on remote connections because of port forwarding
                    try (DatagramSocket ds = new DatagramSocket()) {
                        //Creates a fake UDP connection only to read the IP that the UDP interface will use to
                        //communicate with the Server, then the connection is automatically closed thanks to the
                        //try-with resources block.
                        ds.connect(InetAddress.getByName(ServerConfig.SERVER_IP_ADDRESS), 1);

                        //Sets the RMI hostname correctly in the System Properties
                        System.setProperty("java.rmi.server.hostname",
                                ds.getLocalAddress().getHostAddress());

                        System.out.println("IP USED: " + System.getProperty("java.rmi.server.hostname"));
                    }
                    connection = new RmiClient(ServerConfig.SERVER_IP_ADDRESS, ClientConfig.CLIENT_PORT, messageDispatcher);
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

        //This line sends a disconnection request if the game is closed by the outside
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try { controller.disconnect(); } catch (Exception ignored) {}
        }));


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
