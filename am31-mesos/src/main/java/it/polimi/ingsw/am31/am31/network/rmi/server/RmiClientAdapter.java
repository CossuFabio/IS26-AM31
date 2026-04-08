package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.ClientConnection;

import java.rmi.RemoteException;

public class RmiClientAdapter implements ClientConnection {
    private final VirtualViewRmi clientStub;
    public RmiClientAdapter (VirtualViewRmi client) {
        this.clientStub = client;
    }

    @Override
    public void sendUpdate(Object data){
        try{
        clientStub.receiveUpdate(data);
    }catch (RemoteException e){
        System.err.println("RmiClientAdapter.sendUpdate error");
        }
    }
}
