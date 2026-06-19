package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.rmi.client.RmiClient;
import it.polimi.ingsw.am31.am31.network.socket.client.SocketClient;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.StateErrorUpdater;
import it.polimi.ingsw.am31.am31.view.localState.StateUpdater;
import it.polimi.ingsw.am31.am31.view.View;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEventBus;
import it.polimi.ingsw.am31.am31.view.gui.GraphicUserInterface;
import it.polimi.ingsw.am31.am31.view.tui.TextUserInterface;
import org.fusesource.jansi.AnsiConsole;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


/**
 * Entry point for Client. Here they can choose the connection protocol between RMI and Socket and the view type between tui and gui via CMD.
 */
public class Client {
    //main client class
    public static void main(String[] args){
        //System.setOut(new java.io.PrintStream(System.out, true, java.nio.charset.StandardCharsets.UTF_8));
        // may help avoid tui visualization problems
        // [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
        // may want to add this as first command in bat file
        Scanner scanner = new Scanner(System.in);

        // 1. Server IP
        System.out.print("Enter server IP address: ");
        String serverIp = scanner.nextLine().trim();
        ServerConfig.SERVER_IP_ADDRESS = serverIp;

        // 2. Connection protocol
        String connectionType;
        while (true) {
            System.out.println("Select connection type:\n1 - RMI\n2 - Socket");
            System.out.print("> ");
            connectionType = scanner.nextLine().trim();
            if (connectionType.equals("1") || connectionType.equals("2")) break;
            System.out.println("Invalid input, enter 1 or 2.");
        }

        // 3. View type
        String viewType;
        while (true) {
            System.out.println("Select view type:\n1 - TUI\n2 - GUI");
            System.out.print("> ");
            viewType = scanner.nextLine().trim();
            if (viewType.equals("1") || viewType.equals("2")) break;
            System.out.println("Invalid input, enter 1 or 2.");
        }



        //library to display colors
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
                    }
                    connection = new RmiClient(ServerConfig.SERVER_IP_ADDRESS, ClientConfig.CLIENT_PORT, messageDispatcher);
                    break;
                case "2": connection = new SocketClient(ServerConfig.SERVER_IP_ADDRESS, ServerConfig.SERVER_PORT_SOCKET, messageDispatcher);
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

        try{
            view.startView();//after this, the clients acts through the view
        }catch(Exception e){
            System.out.println("An error with the requested view occurred");
        }

    }

}
