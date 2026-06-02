package it.polimi.ingsw.am31.am31.network.rmi.client;


import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Intra-protocol RMI interface exposed by the server; clients call these methods to connect,
 * send requests, and disconnect.
 * <br>Parameters are JSON strings for the same reason as {@link VirtualViewRmi}: so that DTO root classes
 * do not need to implement {@link java.io.Serializable}
 */
public interface VirtualServerRmi extends Remote {

    /**
     * Registers a new RMI client with the server
     *
     * @param identifier the client's initial identifier
     * @param client     the client's {@link VirtualViewRmi} callback stub
     * @throws RemoteException if the RMI call fails
     */
    void connect(String identifier, VirtualViewRmi client) throws RemoteException;

    /**
     * Forwards a JSON-serialized {@code NetworkRequest} from the client to the server
     *
     * @param request  the serialized request
     * @param skeleton the caller's {@link VirtualViewRmi} stub, used to look up the corresponding {@code VirtualView}
     * @throws RemoteException if the RMI call fails
     */
    void sendRequest(String request, VirtualViewRmi skeleton) throws RemoteException;

    /**
     * Notifies the server that the client is disconnecting
     *
     * @param identifier the client's identifier
     * @throws RemoteException if the RMI call fails
     */
    void disconnect(String identifier) throws RemoteException;
}
