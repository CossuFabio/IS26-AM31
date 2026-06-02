package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;

import java.rmi.RemoteException;

/**
 * Wraps a remote {@link VirtualViewRmi} callback stub as a local {@code VirtualView}.
 * The server always works with {@code VirtualView}. This wrapper allows the server to send the client response messages.
 */
public class RmiClientAdapter implements VirtualView {
    private final VirtualViewRmi clientStub;
    private volatile long lastTimeSeen;
    private final RmiServer owner;

    /**
     * @param client the client's remote callback stub
     * @param owner  the {@link RmiServer} that owns this adapter
     */
    public RmiClientAdapter (VirtualViewRmi client, RmiServer owner) {
        this.clientStub = client;
        lastTimeSeen = System.currentTimeMillis();
        this.owner = owner;
    }

    @Override
    public void receiveUpdate(UpdateMessage updateMessage){
        try{
            clientStub.receiveUpdate(UpdateMapper.serialize(updateMessage));
        }catch (RemoteException e){
            System.err.println("RmiClientAdapter.receiveUpdate error");
        }catch(Exception e){
            System.err.println("RmiClientAdapter.receiveUpdate error");
            e.printStackTrace();
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
