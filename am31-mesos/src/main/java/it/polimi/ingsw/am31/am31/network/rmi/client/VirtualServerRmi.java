package it.polimi.ingsw.am31.am31.network.rmi.client;

import it.polimi.ingsw.am31.am31.exceptions.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ServerConnection;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServerRmi extends Remote {

    //should have methods rmiClients call on the server.
    void connect(String identifier, VirtualViewRmi clientStub) throws RemoteException;
    void sendRequest (NetworkRequest request) throws RemoteException;
    void createGame (int nplayers) throws RemoteException ,IOException;
    void joinGameLobby(String nickname, Color color, int i) throws RemoteException, TooManyPlayersException;
    void showLobbies() throws RemoteException;

}
