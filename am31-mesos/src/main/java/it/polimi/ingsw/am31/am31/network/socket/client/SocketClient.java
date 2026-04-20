package it.polimi.ingsw.am31.am31.network.socket.client;

import it.polimi.ingsw.am31.am31.network.VirtualServer;

import java.io.BufferedReader;

public class SocketClient implements VirtualViewSocket{
    private final String identifier;
    private final BufferedReader input;

    public SocketClient(String identifier, BufferedReader input){
        this.identifier = identifier;
        this.input = input;
    }

}
