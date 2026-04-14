package it.polimi.ingsw.am31.am31.network.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualViewRmi extends Remote {
    //should have methods the server calls on its rmi clients
    void receiveUpdate(Object data) throws RemoteException;
    void receiveMessage(List<String> data) throws RemoteException;


}
