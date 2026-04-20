package it.polimi.ingsw.am31.am31.network.socket.server;

import it.polimi.ingsw.am31.am31.network.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements VirtualServerSocket{

    private final ServerSocket listenSocket;
    private final Server mainServer;

    public SocketServer(ServerSocket listenSocket, Server mainServer){
        this.listenSocket = listenSocket;
        this.mainServer = mainServer;
    }


    public void start(){
        Socket client = null;
        try {
            while ((client = listenSocket.accept())!= null) {
                InputStreamReader socketRx = new InputStreamReader(client.getInputStream());
                OutputStreamWriter socketTx = new OutputStreamWriter(client.getOutputStream());
                SocketClientHandler clientHandler =  new SocketClientHandler(mainServer, this, new BufferedReader(socketRx), new PrintWriter(socketTx));

                new Thread(()->{
                    try{
                        clientHandler.runVirtualView();
                    }catch(RuntimeException e){
                        System.err.println(e.getMessage());
                    }

                });


            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }


}
