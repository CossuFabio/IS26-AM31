package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.exceptions.networkException.BadNetworkRequestException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.UsernameAlreadyInUseException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.UsernameNotRegisteredException;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateFactory;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.DisconnectNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;
import it.polimi.ingsw.am31.am31.network.socket.server.SocketServer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    //map of all connected players with their id
    private final Map<String, VirtualView> clients;
    //gamesManager, contains games and controllers
    private final GamesManager gamesManager;

    //This Set keeps track of all connections that haven't been registered with a username. A background thread
    //periodically check their registration time and if they do not register, the connection is closed.
    private final Set<VirtualView> waitingRoom;

    public Server() {
        this.gamesManager = new GamesManager();
        this.clients = new ConcurrentHashMap<>();
        this.waitingRoom = ConcurrentHashMap.newKeySet();

    }

    //Routing the request and verify the validity. Since this is the only entry point to the server, passing this validty
    //Test means that we do not need to always check the validities!
    public void handleNetworkRequest (NetworkRequest request, VirtualView view){
        try{//Server cannot do anything

            if (view == null) return;
            if(!waitingRoom.contains(view))
                view.updateLastTime();

            //Don't care if pinging
            if (request == null || request.getType().equals(PingNetworkRequest.METHOD)) return;

            if (!request.checkValidity()) {
                String type = (request != null && request.getType() != null) ? request.getType() : "Unknown type";
                view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException(type)));
                return;
            }

            VirtualView inServerClientView = clients.get(request.getPlayerID());
            if (inServerClientView == null && !request.getType().equals(NewServerConnectionRequest.METHOD)) {
                System.out.println("Richiesta da utente non valido ricevuta");
                view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new UsernameNotRegisteredException()));
                return;
            }

            //Blocks spoofing
            if (!request.getType().equals(NewServerConnectionRequest.METHOD) && inServerClientView != view) {
                view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException("Wrong username!")));
                return;
            }

            //Received new connection
            if (request.getType().equals(NewServerConnectionRequest.METHOD)) {
                //This method will handle success or failure
                String newId = ((NewServerConnectionRequest) request).getRequestedUsername();
                addClient(newId, view);
                return;
            }

            //Routes for disconnections
            if (request.getType().equals(DisconnectNetworkRequest.METHOD)) {
                disconnect(request.getPlayerID());
                return;
            }

            //Else: received a game-related request that will be handled by the GamesManager
            gamesManager.handleRequest(request, view);

        }catch(Exception e){ //Saves the server in case of unchecked exceptions
            System.err.println(e.getMessage());
        }


    }



    public void start() {
        final String serverName = ServerConfig.SERVER_NAME;
        //Rmi server  launch

        try{
            System.out.println("Server starting on ip: " + InetAddress.getLocalHost().getHostAddress());
        }catch(Exception e){
            System.out.println("Unable to setup server");
        }

        Thread rmiThread = new Thread(() -> {
            try {
                new RmiServer(serverName, ServerConfig.SERVER_PORT_RMI, this).start();
                System.out.println("RmiServer on");
            } catch (RemoteException e) {
                System.out.println("RmiServer Fail" + e.getMessage());
            } catch (UnknownHostException e) {
                System.out.println("Failed to start RMI server!");
            }catch (Exception e) {
                System.out.println("Failed to start socket server!");
            }

        });
        rmiThread.start();


        //SocketServer launch
        Thread socketThread = new Thread(() -> {
            try {
                new SocketServer(new ServerSocket(ServerConfig.SERVER_PORT_SOCKET), this).start();


            } catch (Exception e) {
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
                        if(time - v.getValue().getLastTime() > ServerConfig.HEARTBEAT_TIMEOUT) {
                            disconnect(v.getKey());
                        }
                    }

                    for(VirtualView v : waitingRoom){
                        if(time - v.getLastTime() > ServerConfig.WAITING_ROOM_TIMEOUT){
                            if(waitingRoom.remove(v)){
                                v.forceDisconnect();
                            }
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
            //Stop the thread in SocketClientHandler or removes from the adapters map in RMIServer
            VirtualView disconnectedClient = clients.remove(id);
            if(disconnectedClient != null) disconnectedClient.forceDisconnect();

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
        if(clients.putIfAbsent(identifier, virtualView) != null) {
            virtualView.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new UsernameAlreadyInUseException(identifier)));
            return;
        }

        System.out.println("Client " + identifier + " has been added");

        try{
            virtualView.receiveUpdate(UpdateFactory.createSuccessRegistrationUpdate(identifier));
            waitingRoom.remove(virtualView);
        }catch(Exception e){
            System.out.println("Unable to notify client. Removing it from the list");
            clients.remove(identifier, virtualView);
        }


    }

    public void registerWaitingRoom(VirtualView view){
        try{
            if(view != null) waitingRoom.add(view);
        }catch(Exception e){
            //Shouldn't throw any exception but better check
            System.out.println("Error in registerWaitingRoom: ");
            e.printStackTrace();

        }

    }


}
