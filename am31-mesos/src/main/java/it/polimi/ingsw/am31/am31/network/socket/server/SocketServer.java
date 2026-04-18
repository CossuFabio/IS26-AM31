package it.polimi.ingsw.am31.am31.network.socket.server;

import it.polimi.ingsw.am31.am31.network.Server;

import java.net.ServerSocket;

public class SocketServer {

    private final ServerSocket listenSocket;
    private final Server mainServer;

    public SocketServer(ServerSocket listenSocket, Server mainServer){
        this.listenSocket = listenSocket;
        this.mainServer = mainServer;
    }



}
