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

public class RmiServer extends UnicastRemoteObject implements VirtualServerRmi {
    private int port;
    private Server mainServer;
    private String serverName;

    public RmiServer(String serverName,int port, Server mainServer) throws RemoteException {
        super(port); //RMI usa una porta dinamica per l'oggetto remoto --> problema con i firewall
        this.port=port;
        this.serverName=serverName;
        this.mainServer=mainServer;
    }

    @Override
    public void disconnect() throws RemoteException {

    }

    @Override
    public void sendRequest(String request) throws RemoteException {
        try {
            System.out.println("ADESSO LEGGOU UNA RICHIESTA");
            NetworkRequest req = RequestsMapper.deserialize(request);
            System.out.println("RICHIESTA DI " + req.getType());
            mainServer.handleNetworkRequest(RequestsMapper.deserialize(request));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public void connect(String identifier ,VirtualViewRmi client) throws RemoteException {
        //more clients could invoke this
        //we create a rmiadapter and add him to rmi server clients
            VirtualView rmiClient = new RmiClientAdapter(client);
            mainServer.addClient(identifier,rmiClient);
            System.out.println("Connected "+identifier);
    }


    public void start () throws RemoteException, UnknownHostException {
        //java uses local hostname by default, not reachable by other machines
        //added UnknownHostException
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName,this);
    }


}
