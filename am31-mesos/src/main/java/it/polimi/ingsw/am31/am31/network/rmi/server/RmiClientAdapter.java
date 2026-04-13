package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.ClientConnection;

import java.rmi.RemoteException;

public class RmiClientAdapter implements ClientConnection {
    private final VirtualViewRmi clientStub;
    private GameController controller;
    public RmiClientAdapter (VirtualViewRmi client) {
        this.clientStub = client;
        this.controller = null;
    }
    //adapter contains the clients callback in clientStub, calls methods on the stub.
    //adapter implements ClintConnection methods, callable by server on its clients
    @Override
    public void sendUpdate(Object data){
        try{
        clientStub.receiveUpdate(data);
    }catch (RemoteException e){
        System.err.println("RmiClientAdapter.sendUpdate error");
        }
    }
    @Override
    public void setGameController (GameController controller) {
        this.controller = controller;
    }
}
