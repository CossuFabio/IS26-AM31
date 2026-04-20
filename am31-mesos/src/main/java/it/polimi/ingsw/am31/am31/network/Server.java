package it.polimi.ingsw.am31.am31.network;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.PlayerAlreadyInGameException;
import it.polimi.ingsw.am31.am31.network.requests.*;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateFactory;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMapper;
import org.w3c.dom.html.HTMLImageElement;


import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    //map of all connected players with their id
    private final Map<String, VirtualView> clients;
    //map of connected players with their last seen time
    private final Map<String, Long> ClientsLastSeen;
    //gamesManager, contains games and controllers
    private final GamesManager gamesManager;

    public Server() {
        this.gamesManager = new GamesManager();
        this.clients = new ConcurrentHashMap<>();
        this.ClientsLastSeen = new ConcurrentHashMap<>();
    }


    public void handleNetworkRequest (NetworkRequest request) throws Exception {

        if(request == null){
            System.out.println("Stringa vuota!");
            return;
        }
        if(!clients.containsKey(request.getPlayerID()))
        {
         //   System.out.println("illegal request received");
            throw new IllegalAccessException("illegal request received");
        }
        ClientsLastSeen.put(request.getPlayerID(), System.currentTimeMillis());
        if(request.getType().equals(RequestMethodsConstants.PING)) {return;}

        if(request.getType().equals(RequestMethodsConstants.METHOD_JOIN_GAME)) {

            JoinNetworkRequest req = (JoinNetworkRequest) request;
            System.out.println(req.getPlayerID() + " sta provando ad entrare nel tubo " + req.getGameID() + " col colore: " + req.getColor());
            joinGameLobby(req);
        }
        else if(request.getType().equals(RequestMethodsConstants.METHOD_SHOW_LOBBIES)) {
            ShowLobbyNetworkRequest req = (ShowLobbyNetworkRequest) request;
            showLobbies(req);

        }
        else if(request.getType().equals(RequestMethodsConstants.METHOD_NEW_GAME)) {
            NewGameNetworkRequest req = (NewGameNetworkRequest) request;
            createNewLobby(req);
        }
        else if(request.getType().equals(RequestMethodsConstants.METHOD_DRAW)) {

        }
        else if(request.getType().equals(RequestMethodsConstants.METHOD_PLACE_TOTEM)) {

        }

        else if(request.getType().equals("")) {

        }

        else if(request.getType().equals("")) {

        }

    }


    //method to add a generic connection to map
    public void addClient(String identifier, VirtualView virtualView) {

        clients.put(identifier, virtualView);
        ClientsLastSeen.put(identifier, System.currentTimeMillis());
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
                System.out.println("RMI server started");
            } catch (RemoteException e) {
                System.out.println("RmiServer Fail" + e.getMessage());

                //modifica: aggiunto un altro catch per la nuova eccezione
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        });
        rmiThread.setDaemon(true);
        rmiThread.start();

        //SocketServer launch
        //TODO Thread socketThread = new Thread(() -> { new SocketServer(serverName,1100).start();});
        Thread pingThread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(10000);
                    long time = System.currentTimeMillis();
                    for(String id : ClientsLastSeen.keySet()) {
                        if(time - ClientsLastSeen.get(id) > 11000)
                        {
                            disconnect(id); //removes him form last seen, form clients list, sends message to everyone else
                    }
                }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pingThread.start();
    }
    public void disconnect(String id) throws Exception {
        clients.remove(id);
        ClientsLastSeen.remove(id);
        System.out.println("Client " + id + " has been disconnected");
        for(VirtualView v : clients.values()) {
            v.receiveMessage("User "+id+" Has left");
        }

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
            controller.handleAddPlayerMessage(request); //(request, connection)
            VirtualView connection = clients.get(request.getPlayerID());

        }
    }

}
