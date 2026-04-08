package it.polimi.ingsw.am31.am31.network;

import java.rmi.RemoteException;

public interface ClientConnection {
    //methods called by server on its clients, to send them updates and such
    public void sendUpdate(Object data);
}
