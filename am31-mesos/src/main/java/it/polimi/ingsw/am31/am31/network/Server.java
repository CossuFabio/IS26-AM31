package it.polimi.ingsw.am31.am31.network;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.PlayerAlreadyInGameException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.network.requests.*;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;
import it.polimi.ingsw.am31.am31.network.socket.server.SocketServer;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateFactory;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;


public class Server {

    //map of all connected players with their id
    private final Map<String, VirtualView> clients;
    //gamesManager, contains games and controllers
    private final GamesManager gamesManager;

    //map that associates each string-type with the method to call
    private final Map<String, Consumer<NetworkRequest>> commands = new HashMap<>();

    public Server() {
        this.gamesManager = new GamesManager();
        this.clients = new ConcurrentHashMap<>();

        commands.put(RequestMethodsConstants.METHOD_JOIN_GAME, r -> {
            try {
                joinGameLobby((JoinNetworkRequest) r);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                System.err.println("Error to join the game: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_SHOW_LOBBIES, r -> {
            try {
                showLobbies((ShowLobbyNetworkRequest)r);
            } catch (Exception e) {
               //throw new RuntimeException(e);
                System.err.println("Error to show the lobbies: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_NEW_GAME, r -> {
            try {
                createNewLobby((NewGameNetworkRequest) r);
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.err.println("Error to create a lobby: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_DRAW, r-> {
            try {
                DrawNetworkRequest req = (DrawNetworkRequest) r;
                GameController ctrl = gamesManager.getGameControllerWithPlayer(req.getPlayerID());
                ctrl.handleDraw(req);
            } catch (PlayerNotFoundException e) {
                //throw new RuntimeException(e);
                System.err.println("Player not found: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_PLACE_TOTEM, r-> {
            try {
                TotemNetworkRequest req = (TotemNetworkRequest) r;
                GameController ctrl = gamesManager.getGameControllerWithPlayer(req.getPlayerID());
                ctrl.handleTotemAction(req);
            } catch (PlayerNotFoundException e) {
                //throw new RuntimeException(e);
                System.err.println("Error to place the totem: " + e.getMessage());
            }
        });
    }


    public void handleNetworkRequest (NetworkRequest request) {
        if(request == null || request.getType() == null){
            System.out.println("Stringa vuota!");
            return;
        }

        if(commands.containsKey(request.getType())){
            commands.get(request.getType()).accept(request);
        }
        else {
            System.err.println("Unknow request type: " + request.getType());
        }


    }


    //method to add a generic connection to map
    public void addClient(String identifier, VirtualView virtualView) {

        clients.put(identifier, virtualView);
        System.out.println("Client " + identifier + " has been added");
        //TODO add listener threads when accepting socket connection
    }


    //old main put into start method
    public void start() {
        final String serverName = ServerConfig.SERVER_NAME;

        //Rmi server  launch
        Thread rmiThread = new Thread(() -> {
            try {
                new RmiServer(serverName, ServerConfig.SERVER_PORT_RMI, this).start();
                System.out.println("RmiServer on");
            } catch (RemoteException e) {
                System.out.println("RmiServer Fail" + e.getMessage());

                //modifica: aggiunto un altro catch per la nuova eccezione
            } catch (UnknownHostException e) {
                System.out.println("Failed to start RMI server!");
            }
        });
        rmiThread.start();


        //SocketServer launch
        Thread socketThread = new Thread(() -> {
            try {
                new SocketServer(new ServerSocket(ServerConfig.SERVER_PORT_SOCKET), this).start();
                System.out.println("SocketServer on");

            } catch (IOException e) {
                System.out.println("Failed to start socket server!");
            }
        });
        socketThread.start();
    }


    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        server.start();
    }

    //methods for creating a new game (on player request), showing active games (on player request)
    //these methods creates a new gameController -> a new game, with 0 players in.
    public void createNewLobby(NewGameNetworkRequest req) throws IOException {
        try {
            System.out.println("Richiesta nuova partita da "+ req.getnplayers() + " giocatori");
            gamesManager.createGame(req.getnplayers());
        } catch (IOException e) {

        }
    }

    public void showLobbies(ShowLobbyNetworkRequest request) throws Exception {
        VirtualView requester = clients.get(request.getPlayerID());
        if(requester!= null) {
            requester.receiveUpdate(UpdateFactory.createShowLobbyUpdate(gamesManager));
        }
    }

    public void joinGameLobby(JoinNetworkRequest request) throws Exception {
        GameController controller = gamesManager.getControllerI(request.getGameID());
        if(controller!=null) {
        //checks if player is already in game
            if(controller.isPlayerInGame(request.getPlayerID())) {throw new PlayerAlreadyInGameException(request.getPlayerID());}
            GameObserver obs = new NetworkObserver(clients.get(request.getPlayerID()));
            controller.handleAddPlayerMessage(request,obs); //(request, connection)
        }
        //TODO: notificare il client se la lobby non esiste
    }

}
