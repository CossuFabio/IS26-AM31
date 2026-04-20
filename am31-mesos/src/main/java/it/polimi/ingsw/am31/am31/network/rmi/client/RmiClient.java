package it.polimi.ingsw.am31.am31.network.rmi.client;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.ServerConfig;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateVisitor;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class RmiClient extends UnicastRemoteObject implements VirtualServer, VirtualViewRmi {
    //extends both, to allow callback from server

    private final VirtualServerRmi serverStub;
    private final String identifier;
    private VirtualViewRmi clientStub;

    public RmiClient(String ip, int port, String identifier) throws RemoteException, NotBoundException {
       super(port);
        this.identifier = identifier;
        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,ServerConfig.SERVER_PORT_RMI);
        this.serverStub = (VirtualServerRmi) registry.lookup(ServerConfig.SERVER_NAME);
        //sends himself to server
        this.serverStub.connect(this.identifier,this);
    }
    @Override
    public void sendRequest(NetworkRequest request) throws RemoteException {
        request.setPlayerID(this.identifier);
        serverStub.sendRequest(RequestsMapper.serialize(request));
    }

    @Override
    public void receiveMessage (String data) throws RemoteException {
        System.out.println(data);
    }
    @Override
    public void receiveUpdate (String updateMessage) {
        UpdateMessage message = UpdateMapper.deserialize(updateMessage);
        if(message == null || message.getUpdateType() == null) return;
        message.ShowUpdate();
    }
    @Override
    public void disconnect() throws RemoteException{

    }
}
