package it.polimi.ingsw.am31.am31.network.rmi.client;


import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServerRmi extends Remote {

    //should have methods rmiClients call on the server.
    void connect(String identifier, VirtualViewRmi client) throws RemoteException;

    void sendRequest(String request, VirtualViewRmi skeleton) throws RemoteException;

    void disconnect() throws RemoteException;
}
