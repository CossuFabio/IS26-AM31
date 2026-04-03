package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.rmi.client.VirtualViewRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServerRmi extends Remote, VirtualView {
    //TODO:RMI SERVER IMPLEMENTS THIS
    //this interface defines methods callable on RMI object, everything a rmi client can do.
    void connect(String identifier, VirtualViewRmi clientStub) throws RemoteException;
}
