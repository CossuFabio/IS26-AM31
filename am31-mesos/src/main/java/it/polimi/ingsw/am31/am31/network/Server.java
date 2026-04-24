package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;

import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.PlayerAlreadyInGameException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.BadNetworkRequestException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.UsernameAlreadyInUseException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.UsernameNotRegisteredException;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.requests.*;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;
import it.polimi.ingsw.am31.am31.network.socket.server.SocketServer;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;


public class Server {

    //map of all connected players with their id
    private final Map<String, VirtualView> clients;
    //gamesManager, contains games and controllers
    private final GamesManager gamesManager;
    //map that associates each string-type with the method to call
    private final Map<String, BiConsumer<NetworkRequest, VirtualView>> commands = new HashMap<>();

    public Server() {
        this.gamesManager = new GamesManager();
        this.clients = new ConcurrentHashMap<>();

        commands.put(RequestMethodsConstants.METHOD_JOIN_GAME, (r, view )-> {
            try {
                joinGameLobby((JoinGameNetworkRequest) r, view);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                System.err.println("Error to join the game: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_SHOW_LOBBIES, (r, view) -> {
            try {
                showLobbies((ShowLobbyNetworkRequest)r, view);
            } catch (Exception e) {
               //throw new RuntimeException(e);
                System.err.println("Error to show the lobbies: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_NEW_GAME, (r, view ) -> {
            try {
                createNewLobby((NewGameNetworkRequest) r, view);
            } catch (IOException e) {
                //throw new RuntimeException(e);
                System.err.println("Error to create a lobby: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_DRAW, (r, view )-> {
            try {
                DrawNetworkRequest req = (DrawNetworkRequest) r;
                GameController ctrl = gamesManager.getGameControllerWithPlayer(req.getPlayerID());
                //ctrl.handleDraw(req);
            } catch (PlayerNotFoundException e) {
                //throw new RuntimeException(e);
                System.err.println("Player not found: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.METHOD_PLACE_TOTEM, (r, view )-> {
            try {
                TotemNetworkRequest req = (TotemNetworkRequest) r;
                GameController ctrl = gamesManager.getGameControllerWithPlayer(req.getPlayerID());
                //ctrl.handleTotemAction(req);
            } catch (PlayerNotFoundException e) {
                //throw new RuntimeException(e);
                System.err.println("Error to place the totem: " + e.getMessage());
            }
        });
        commands.put(RequestMethodsConstants.PING, (r, view ) -> {
                PingNetworkRequest req = (PingNetworkRequest) r;

        });


        commands.put(RequestMethodsConstants.METHOD_NEW_CONNECTION, (r, view) -> {
                String identifier = r.getPlayerID();
                addClient(identifier, view);
        });

    }

    //Routing the request and verify the validity. Since this is the only entry point to the server, passing this validty
    //Test means that we do not need to always check the validities!
    public void handleNetworkRequest (NetworkRequest request, VirtualView view){
        //Server cannot do anything
        if(view == null) return;

        if(request == null || !request.checkValidity()){
            System.out.println("Stringa vuota!");
            String type = (request != null && request.getType() != null) ? request.getType() : "Unknown type";

            return;
        }
        if(!clients.containsKey(request.getPlayerID()) && !Objects.equals(request.getType(), RequestMethodsConstants.METHOD_NEW_CONNECTION))
        {
            System.out.println("Richiesta da utente non valido ricevuta");
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new UsernameNotRegisteredException()));
            return;
        }

        view.updateLastTime();

        if(commands.containsKey(request.getType())){
            commands.get(request.getType()).accept(request, view);
        }
        else {
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException(request.getType())));
        }


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
            } catch (UnknownHostException e) {
                System.out.println("Failed to start RMI server!");
            }
        });
        rmiThread.start();


        //SocketServer launch
        Thread socketThread = new Thread(() -> {
            try {
                new SocketServer(new ServerSocket(ServerConfig.SERVER_PORT_SOCKET), this).run();


            } catch (IOException e) {
                System.out.println("Failed to start socket server!");
            }
        });
        socketThread.start();
        Thread pingThread = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(ServerConfig.HEARTBEAT_SERVER_INTERVAL);
                    long time = System.currentTimeMillis();
                    for(Map.Entry<String, VirtualView> v : clients.entrySet()) {
                        if(time - v.getValue().getLastTime() > ServerConfig.HEARTBEAT_TIMOUT)
                        {
                             //removes client form clients list, sends message to everyone else
                            disconnect(v.getKey());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pingThread.setDaemon(true);
        pingThread.start();
    }

    public void disconnect(String id) throws Exception {
        clients.remove(id);
        System.out.println(" Client "+id+ " has been disconnected");
        //TODO closes the game aswell
        for(VirtualView g : clients.values()) {
            g.receiveMessage("a User "+id + " has left");
        }
    }

    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        server.start();
    }

    //methods for creating a new game (on player request), showing active games (on player request)
    //these methods creates a new gameController -> a new game, with 0 players in.
    //No visibility operator means not visible outside the package. We want it to mantain the validity of the request
    void createNewLobby(NewGameNetworkRequest req, VirtualView view) throws IOException {
        try {
            System.out.println("Richiesta nuova partita da "+ req.getNumPlayers() + " giocatori");
            gamesManager.createGame(req.getNumPlayers());
        } catch (IOException e) {

        }
    }

    void showLobbies(ShowLobbyNetworkRequest request, VirtualView view) throws Exception {
        VirtualView requester = clients.get(request.getPlayerID());
        if(requester!= null) {
            view.receiveUpdate(UpdateFactory.createShowLobbyUpdate(gamesManager));
        }
    }

    void joinGameLobby(JoinGameNetworkRequest request, VirtualView view) throws Exception {
        VirtualView requester = view;
        if (requester == null)
        {
            System.err.println("Joingame: client not found for player" + request.getPlayerID());
            return;
        }

        GameController controller = gamesManager.getControllerI(request.getGameID());

        if (controller == null)
        {
            requester.receiveErrorMessage(new ErrorMessage("Lobby does not exist!", ErrorCategory.LOBBY_ERROR));
            //throw new LobbyNotFoundException(request.getGameID());
        }
        else
        {
            //checks if player is already in game
            if(controller.isPlayerInGame(request.getPlayerID()))
            {
                requester.receiveErrorMessage(new ErrorMessage("Player already in game!", ErrorCategory.LOBBY_ERROR));
                throw new PlayerAlreadyInGameException(request.getPlayerID());
            }
            GameObserver obs = new NetworkObserver(clients.get(request.getPlayerID()));
            controller.handleAddPlayerMessage(request,obs); //(request, connection)
        }
    }
    //method to register a client via is username
    //Not public!
    void addClient(String identifier, VirtualView virtualView){
        //SOSEW - Debug
        for(String s : clients.keySet()) System.out.println(s);

        if(clients.containsKey(identifier)){
            virtualView.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new UsernameAlreadyInUseException(identifier)));
            return;
        }

        clients.put(identifier, virtualView);
        System.out.println("Client " + identifier + " has been added");
        //Should notify for success
        //TODO add listener threads when accepting socket connection
    }

}
