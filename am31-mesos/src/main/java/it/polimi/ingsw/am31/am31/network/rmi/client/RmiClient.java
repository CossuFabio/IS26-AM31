package it.polimi.ingsw.am31.am31.network.rmi.client;
import it.polimi.ingsw.am31.am31.network.ClientConfig;
import it.polimi.ingsw.am31.am31.network.MessageDispatcher;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.network.ServerConfig;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RmiClient extends UnicastRemoteObject implements VirtualServer, VirtualViewRmi {
    //extends both, to allow callback from server

    private final VirtualServerRmi serverStub;
    private String identifier = ClientConfig.UNREGISTERED_CLIENT_ID;
    private VirtualViewRmi clientStub;
    private LocalGameState gameState;
    private StateUpdater updater;
    private ErrorHandler errorVisitor;
    private final MessageDispatcher messageDispatcher;

    private boolean usernameSet = false;

    public RmiClient(String ip, int port, MessageDispatcher messageDispatcher) throws RemoteException, NotBoundException {

        super(port);

        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,ServerConfig.SERVER_PORT_RMI);
        this.serverStub = (VirtualServerRmi) registry.lookup(ServerConfig.SERVER_NAME);
        //sends himself to RMI server
        this.serverStub.connect(this.identifier,this);

        this.messageDispatcher = messageDispatcher;


    }
    @Override
    public void sendRequest(NetworkRequest request) throws RemoteException {
        request.setPlayerID(this.identifier);
        serverStub.sendRequest(RequestsMapper.serialize(request), this);

    }


    @Override
    public void receiveErrorMessage(String errorMessageString) throws RemoteException {
        ErrorMessage errorMessage = ErrorMessageMapper.deserialize(errorMessageString);
        if(errorMessage == null || !errorMessage.checkValidity()) return;
        messageDispatcher.submit(errorMessage);
    }


    @Override
    public void receiveUpdate (String updateMessage) {
        UpdateMessage message = UpdateMapper.deserialize(updateMessage);
        if(message == null || !message.checkValidity()) return;
        messageDispatcher.submit(message);
    }

    @Override
    public void disconnect() throws RemoteException{
        try {
            serverStub.disconnect(this.identifier);
            messageDispatcher.shutdown();
            UnicastRemoteObject.unexportObject(this, true);
        } catch (Exception e) {
            System.err.println("Disconnection failed: " + e.getMessage());
        }
    }

    @Override
    public void setIdentifier(String identifier){
        if(!usernameSet){
            this.identifier = identifier;
            usernameSet = true;
        }
    }


}
