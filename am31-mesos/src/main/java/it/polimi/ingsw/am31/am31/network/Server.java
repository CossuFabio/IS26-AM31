package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.exceptions.networkException.BadNetworkRequestException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.UsernameAlreadyInUseException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.UsernameNotRegisteredException;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;
import it.polimi.ingsw.am31.am31.network.socket.server.SocketServer;
import it.polimi.ingsw.am31.am31.network.updateMessages.serverMessages.SuccessRegistrationUpdate;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    //map of all connected players with their id
    private final Map<String, VirtualView> clients;
    //gamesManager, contains games and controllers
    private final GamesManager gamesManager;

    public Server() {
        this.gamesManager = new GamesManager();
        this.clients = new ConcurrentHashMap<>();

    }

    //Routing the request and verify the validity. Since this is the only entry point to the server, passing this validty
    //Test means that we do not need to always check the validities!
    public void handleNetworkRequest (NetworkRequest request, VirtualView view){
        //Server cannot do anything
        if(view == null) return;

        if(request == null || !request.checkValidity()){
            String type = (request != null && request.getType() != null) ? request.getType() : "Unknown type";
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException(type)));
            return;
        }

        if(!clients.containsKey(request.getPlayerID()) && !request.getType().equals((RequestMethodsConstants.METHOD_NEW_CONNECTION))){
            System.out.println("Richiesta da utente non valido ricevuta");
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new UsernameNotRegisteredException()));
            return;
        }

        //Blocks spoofing
        if(!request.getType().equals(RequestMethodsConstants.METHOD_NEW_CONNECTION) && clients.get(request.getPlayerID()) != view){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException("Wrong username!")));
            return;
        }

        view.updateLastTime();

        //Don't care if pinging
        if(request.getType().equals(RequestMethodsConstants.PING)) return;

        //Received new connection
        if(request.getType().equals(RequestMethodsConstants.METHOD_NEW_CONNECTION)) {
            //This method will handle success or failure
            addClient(request.getPlayerID(), view);
            return;
        }

        //Routes for disconnections
        if(request.getType().equals((RequestMethodsConstants.METHOD_DISCONNECT))){
            disconnect(request.getPlayerID());
            return;
        }

        //Else: received a game-related request that will be handled by the GamesManager
        gamesManager.handleRequest(request, view);


    }


    //old main put into start method TODO: Some bugs with exceptions - FIX
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
                        if(time - v.getValue().getLastTime() > ServerConfig.HEARTBEAT_TIMEOUT)
                        {
                             //removes client form clients list, sends message to everyone else
                            disconnect(v.getKey());
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });
        pingThread.setDaemon(true);
        pingThread.start();
    }

    public void disconnect(String id){
        try {
            clients.remove(id);
            System.out.println(" Client " + id + " has been disconnected");
            gamesManager.handleDisconnect(id);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        server.start();
    }

    //method to register a client via his username
    //Not public!
    void addClient(String identifier, VirtualView virtualView){

        //Better explanation for putIfAbsent in the method in GameController that adds a new player
        if(clients.putIfAbsent(identifier, virtualView) != null){
            virtualView.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new UsernameAlreadyInUseException(identifier)));
            return;
        }

        System.out.println("Client " + identifier + " has been added");
        try{
            virtualView.receiveUpdate(new SuccessRegistrationUpdate(identifier));
        }catch(Exception e){
            System.out.println("Unable to notify client. Removing it from the list");
            clients.remove(identifier, virtualView);
        }

        //TODO add listener threads when accepting socket connection
    }

}
