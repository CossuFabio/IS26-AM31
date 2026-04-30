package it.polimi.ingsw.am31.am31.network.rmi.server;


import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.client.VirtualServerRmi;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RmiServer extends UnicastRemoteObject implements VirtualServerRmi {
    private final int port;
    private final Server mainServer;
    private final String serverName;

    private final Map<VirtualViewRmi, VirtualView> adaptersMap;




    public RmiServer(String serverName,int port, Server mainServer) throws RemoteException {
        super(port); //RMI usa una porta dinamica per l'oggetto remoto --> problema con i firewall
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
            System.out.println("Received: " + request);
            mainServer.handleNetworkRequest(req, requestor);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void connect(String identifier ,VirtualViewRmi client) throws RemoteException {
        //more clients could invoke this
        //we create a rmiadapter and add him to rmi server clients
            if(client == null || identifier == null) {
                System.out.println("Received null skeleton");
                return;
            } //Impossible to send response to null client
            VirtualView rmiClient = new RmiClientAdapter(client, this);

            adaptersMap.put(client, rmiClient);
            mainServer.registerWaitingRoom(rmiClient);

    }


    public void start () throws RemoteException, UnknownHostException {
        //java uses local hostname by default, not reachable by other machines
        //added UnknownHostException
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName,this);
    }

    public void removeAdapter(VirtualViewRmi adapter){this.adaptersMap.remove(adapter);}


}
