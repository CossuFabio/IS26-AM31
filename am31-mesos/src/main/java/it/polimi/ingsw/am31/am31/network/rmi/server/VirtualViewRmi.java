package it.polimi.ingsw.am31.am31.network.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualViewRmi extends Remote {
    //should have methods the server calls on its rmi clients
    void receiveUpdate(String data) throws RemoteException;
    void receiveMessage(String data) throws RemoteException;
    void receiveErrorMessage(String errorMessage) throws RemoteException;



}
