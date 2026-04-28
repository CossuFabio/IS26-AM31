package it.polimi.ingsw.am31.am31.network.socket.server;

import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer extends Thread implements VirtualServerSocket{

    private final ServerSocket listenSocket;
    private final Server mainServer;

    public SocketServer(ServerSocket listenSocket, Server mainServer){
        this.listenSocket = listenSocket;
        this.mainServer = mainServer;
    }

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
                    // continua ad accettare altri client
                }
            }
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }


}
