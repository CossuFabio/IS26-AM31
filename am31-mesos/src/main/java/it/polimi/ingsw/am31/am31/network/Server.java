package it.polimi.ingsw.am31.am31.network;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;


import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    //map of all connected players with their id
    private final Map<String, ClientConnection> clients;
    //gamesManager, contains games and controllers
    private final GamesManager gamesManager;

    public Server() {
        this.gamesManager = new GamesManager();
        this.clients = new ConcurrentHashMap<>();
    }


    //method to add a generic connection to map
    public void addClient(String identifier, ClientConnection clientConnection) {
        clients.put(identifier, clientConnection);
        System.out.println("Client " + identifier + " has been added");
        //TODO add listener threads when accepting socket connection
    }

    //sends update to all clients
    public void broadcastUpdate(Object data) throws RemoteException {

        for (ClientConnection client : clients.values()) {
            client.sendUpdate(data);
        }
    }

    //old main put into start method
    public void start() {
        final String serverName = "MesosServer";

        //Rmi server  launch
        Thread rmiThread = new Thread(() -> {
            try {
                new RmiServer(serverName, 1100, this).start();
            } catch (RemoteException e) {
                System.out.println("RmiServer Fail");

                //modifica: aggiunto un altro catch per la nuova eccezione
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        });
        rmiThread.start();
        System.out.println("RmiServer on"); //Viene stampato immediatamente senza aspettare che sia effettivamente partito

        //SocketServer launch
        //TODO Thread socketThread = new Thread(() -> { new SocketServer(serverName,1100).start();});
    }


    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
        server.start();
    }

    //TODO methods for; creating a new game (on player request), showing active games (on player request)

    //this methods creates a new gameController -> a new game, with 0 players in.
    public void createNewLobby(int nplayers) throws IOException {
        try {
            gamesManager.createGame(nplayers);
        } catch (IOException e) {

        }
    }

    public void showLobbies() {
        gamesManager.showActiveGames();
    }

    //Method for joining a game.
    public void joinGameLobby(String nickname, Color color, int i) throws TooManyPlayersException {
        GameController controller = gamesManager.getControllerI(i);
        if (controller != null && !controller.isPlayerInGame(nickname)) {
            try{
                controller.getGame().addPlayer(new Player(nickname, color));
            }catch (TooManyPlayersException e)
            ClientConnection connection = clients.get(nickname);
            //to implement direct controller acces
            if (connection != null)
                connection.setGameController(controller);

        }
    }
}
