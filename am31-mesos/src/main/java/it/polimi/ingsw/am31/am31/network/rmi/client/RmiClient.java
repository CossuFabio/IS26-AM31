package it.polimi.ingsw.am31.am31.network.rmi.client;
import it.polimi.ingsw.am31.am31.exceptions.networkException.ConnectionLostException;
import it.polimi.ingsw.am31.am31.network.ClientConfig;
import it.polimi.ingsw.am31.am31.network.MessageDispatcher;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.ErrorHandler;
import it.polimi.ingsw.am31.am31.network.ServerConfig;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.StateUpdater;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implements both the {@link VirtualServer} interface so the client is able to create an instance of this class to send
 * requests but implements also {@link VirtualViewRmi} so the RMI server is able to send response messages to this callback stub.
 * <br> Requests are sent on a single daemon thread so server communication is not blocking for the server and for the client
 * main threads.
 * It is important to set the java.rmi.server.hostname system variable before creating and starting this object. Otherwise,
 * super(port) could export a wrong ip interface to the server, making impossible for it to send responses.
 */
public class RmiClient extends UnicastRemoteObject implements VirtualServer, VirtualViewRmi {
    //extends both, to allow callback from server

    private final VirtualServerRmi serverStub;
    private String identifier = ClientConfig.UNREGISTERED_CLIENT_ID;
    private final MessageDispatcher messageDispatcher;
    private final ExecutorService requestSender;
    private AtomicBoolean stillConnected = new AtomicBoolean(false);
    private final AtomicBoolean disconnected = new AtomicBoolean(false);
    private boolean usernameSet = false;

    /**
     * Creates an instance of RMIClient.
     * WARNING: It is important to set the java.rmi.server.hostname system variable before creating and starting this object.
     * @param ip the server ip
     * @param port the RMIServer port
     * @param messageDispatcher the object that will route the messages
     * @throws RemoteException if the export fails
     * @throws NotBoundException if the registry binding fails
     */
    public RmiClient(String ip, int port, MessageDispatcher messageDispatcher) throws RemoteException, NotBoundException {

        super(port);

        // Handles cases where no response arrives even if the TCP connection is still active
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "5000");

        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,ServerConfig.SERVER_PORT_RMI);
        this.serverStub = (VirtualServerRmi) registry.lookup(ServerConfig.SERVER_NAME);
        //sends himself to RMI server
        this.serverStub.connect(this.identifier,this);

        this.messageDispatcher = messageDispatcher;

        //Makes it Daemon so it closes when the process shuts down
        this.requestSender = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        stillConnected.set(true);

    }
    @Override
    public void sendRequest(NetworkRequest request) throws RemoteException {
        if (requestSender.isShutdown() || !stillConnected.get() ) return;
        request.setPlayerID(this.identifier);
        requestSender.submit(() -> {
            try {
                if (!stillConnected.get()) return;
                serverStub.sendRequest(RequestsMapper.serialize(request), this);
            } catch (Exception e) {
                if (stillConnected.compareAndSet(true, false)) {
                    messageDispatcher.submit(ErrorMessageFactory.createErrorMessage(new ConnectionLostException()));
                }
                try { disconnect(); } catch (Exception ignored) {}
            }
        });
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
        if (!disconnected.compareAndSet(false, true)) return;
        try {
            serverStub.disconnect(this.identifier);
            messageDispatcher.shutdown();
            requestSender.shutdown();
            UnicastRemoteObject.unexportObject(this, true);
        } catch (Exception ignored) {}
    }

    @Override
    public void setIdentifier(String identifier){
        if(!usernameSet){
            this.identifier = identifier;
            usernameSet = true;
        }
    }


}
