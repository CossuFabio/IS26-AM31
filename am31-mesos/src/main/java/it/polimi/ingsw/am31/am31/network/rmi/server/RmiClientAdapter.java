package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;

import java.rmi.RemoteException;

public class RmiClientAdapter implements VirtualView {
    private final VirtualViewRmi clientStub;
    private volatile long lastTimeSeen;
    private final RmiServer owner;


    public RmiClientAdapter (VirtualViewRmi client, RmiServer owner) {
        this.clientStub = client;
        lastTimeSeen = System.currentTimeMillis();
        this.owner = owner;
    }
    //adapter contains the clients callback in clientStub, calls methods on the stub.
    //adapter implements ClintConnection methods, callable by server on its clients

    @Override
    public void receiveUpdate(UpdateMessage updateMessage){
        try{
            clientStub.receiveUpdate(UpdateMapper.serialize(updateMessage));
        }catch (RemoteException e){
            System.err.println("RmiClientAdapter.receiveUpdate error");
        }catch(Exception e){
            System.err.println("RmiClientAdapter.receiveUpdate error" + e.getMessage());
        }
    }

    @Override
    public void receiveMessage(String data) throws RemoteException {
        try{
            clientStub.receiveMessage(data);
        }catch(RemoteException e){
            System.err.println("RmiClientAdapter.receiveMessage error");
        }catch(Exception e){
            System.err.println("RmiClientAdapter.receiveMessage error" + e.getMessage());
        }
    }

    @Override
    public void receiveErrorMessage(ErrorMessage error) {
        try{
            clientStub.receiveErrorMessage(ErrorMessageMapper.serialize(error));
        }catch (RemoteException e){
            System.err.println("RmiClientAdapter.receiveUpdate error");
        }catch(Exception e){
            System.err.println("RmiClientAdapter.receiveUpdate error" + e.getMessage());
        }
    }
    @Override
    public void updateLastTime() {
     this.lastTimeSeen = System.currentTimeMillis();
    }


    @Override
    public long  getLastTime() {
        return lastTimeSeen;
    }

    @Override
    public void forceDisconnect() {
        //Forces the removal of the stub from the adaptersMap in RMIServer
        owner.removeAdapter(clientStub);
    }
}
