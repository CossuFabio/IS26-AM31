package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.VirtualView;

import java.rmi.RemoteException;
import java.util.List;

public class RmiClientAdapter implements VirtualView {
    private final VirtualViewRmi clientStub;
    private GameController controller;
    public RmiClientAdapter (VirtualViewRmi client) {
        this.clientStub = client;
        this.controller = null;
    }
    //adapter contains the clients callback in clientStub, calls methods on the stub.
    //adapter implements ClintConnection methods, callable by server on its clients
    @Override
    public void receiveUpdate(Object data){
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

    @Override
    public void receiveMessage(List<String> data) throws RemoteException {
     clientStub.receiveMessage(data);
    };
}
