package it.polimi.ingsw.am31.am31.network;
import it.polimi.ingsw.am31.am31.network.rmi.server.RmiServer;


import java.rmi.RemoteException;


public class Server {
    //the main creates both a SocketServer and RmiServer
    //both waiting for a connection
    public static void main(String[] args) throws RemoteException {
        GamesManager gamesManager = new GamesManager();
        final String serverName = "MesosServer";

        //Rmi server  launch
        Thread rmiThread = new Thread(() -> {
            try {
                new RmiServer(serverName, 1100, null).start();
            } catch (RemoteException e) {
                System.out.println("RmiServer Fail");
            }
        });
        System.out.println("RmiServer on");

        //SocketServer launch
        //TODO Thread socketThread = new Thread(() -> { new SocketServer(serverName,1100).start();});


    }
}
