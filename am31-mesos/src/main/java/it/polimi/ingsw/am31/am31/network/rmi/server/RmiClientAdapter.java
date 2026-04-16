package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.VirtualView;

import java.rmi.RemoteException;
import java.util.List;

public class RmiClientAdapter implements VirtualView {
    private final VirtualViewRmi clientStub;

    public RmiClientAdapter (VirtualViewRmi client) {
        this.clientStub = client;

    }
    //adapter contains the clients callback in clientStub, calls methods on the stub.
    //adapter implements ClintConnection methods, callable by server on its clients

    @Override
    public void receiveUpdate(Object data){
        try{
            clientStub.receiveUpdate(data);
        }catch (RemoteException e){
            System.err.println("RmiClientAdapter.receiveUpdate error");
        }
    }


    @Override
    public void receiveMessage(List<String> data) throws RemoteException {
        try{
            clientStub.receiveMessage(data);
        }catch(RemoteException e){
            System.err.println("RmiClientAdapter.receiveMessage error");
        }
    };
}
