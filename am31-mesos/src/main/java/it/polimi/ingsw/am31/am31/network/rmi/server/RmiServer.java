package it.polimi.ingsw.am31.am31.network.rmi.server;


import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.client.VirtualServerRmi;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RMI server exposed to clients; deserializes incoming JSON strings into DTO objects so the core
 * {@link Server} can process them. Keeps a registry of {@link RmiClientAdapter} instances that wrap
 * each {@link VirtualViewRmi} stub into a {@code VirtualView} capable of receiving DTOs
 */
public class RmiServer extends UnicastRemoteObject implements VirtualServerRmi {
    /** RMI registry port */
    private final int port;
    /** Core game server */
    private final Server mainServer;
    /** Registry binding name */
    private final String serverName;
    /** Maps each client stub to its server-side VirtualView adapter */
    private final Map<VirtualViewRmi, VirtualView> adaptersMap;

    /**
     * Creates an RMI server stub exported on the given port.
     * WARNING: java.rmi.server.hostname must be set before calling this method.
     * @param serverName the registry binding name
     * @param port       the RMI registry port; also used to export this object
     * @param mainServer the core server to delegate requests to
     * @throws RemoteException if the RMI runtime fails to export this object
     */
    public RmiServer(String serverName,int port, Server mainServer) throws RemoteException {
        super(0);
        this.port=port;
        this.serverName=serverName;
        this.mainServer=mainServer;
        adaptersMap = new ConcurrentHashMap<VirtualViewRmi, VirtualView>();
    }

    @Override
    public void disconnect(String identifier) throws RemoteException {
        try {
            mainServer.disconnect(identifier);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void sendRequest(String request, VirtualViewRmi client) throws RemoteException {
        try {
            VirtualView requestor = adaptersMap.get(client);
            if(requestor == null){
                System.out.println("Unable to find requestor");
                return;
            }
            NetworkRequest req = RequestsMapper.deserialize(request);
            mainServer.handleNetworkRequest(req, requestor);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void connect(String identifier ,VirtualViewRmi client) throws RemoteException {
            if(client == null || identifier == null) {
                System.out.println("Received null skeleton");
                return;
            }
            VirtualView rmiClient = new RmiClientAdapter(client, this);

            adaptersMap.put(client, rmiClient);
            mainServer.registerWaitingRoom(rmiClient);

    }


    /**
     * Starts the RMI registry and binds this server on server name.
     * WARNING: java.rmi.server.hostname must be set before calling this method
     * (and before constructing this object), as the IP is captured at export time
     *
     * @throws RemoteException if binding or export fails
     */
    public void start() throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName, this);
    }


    /**
     * Removes the adapter associated with the given client stub from the registry.
     * Called when an RMI client disconnects.
     *
     * @param adapter the client stub whose adapter should be removed
     */
    public void removeAdapter(VirtualViewRmi adapter){this.adaptersMap.remove(adapter);}


}
