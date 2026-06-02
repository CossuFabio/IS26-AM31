package it.polimi.ingsw.am31.am31.network.socket.server;

import it.polimi.ingsw.am31.am31.network.Server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread that accepts TCP connections and creates a {@link SocketClientHandler} that handles each one.
 */
public class SocketServer extends Thread {

    private final ServerSocket listenSocket;
    private final Server mainServer;

    /**
     * Creates the Thread
     * @param listenSocket the server socket to accept connections on
     * @param mainServer the server that started this Thread
     */
    public SocketServer(ServerSocket listenSocket, Server mainServer){
        this.listenSocket = listenSocket;
        this.mainServer = mainServer;
    }

    /**
     * Accepts connections in a loop; for each new client creates a {@link SocketClientHandler},
     * registers it with the main server, and starts it in a dedicated thread
     */
    @Override
    public void run(){
        Socket client = null;
        System.out.println("SocketServer on");
        try {
            while ((client = listenSocket.accept()) != null) {
                try {
                    SocketClientHandler clientHandler = new SocketClientHandler(mainServer, client);
                    mainServer.registerWaitingRoom(clientHandler);
                    new Thread(() -> {
                        try {
                            clientHandler.runVirtualView();
                        } catch(RuntimeException e){
                            System.err.println(e.getMessage());
                        }
                    }).start();
                } catch (Exception e) {
                    System.err.println("Failed to set up client handler: " + e.getMessage());
                    try { client.close(); } catch (Exception ignored) {}
                }
            }
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


}
