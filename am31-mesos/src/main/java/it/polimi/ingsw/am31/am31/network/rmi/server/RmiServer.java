package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.ClientConnection;
import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
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
        //vecchia versione: super();

        //modifica:
        super(port); //RMI usa una porta dinamica per l'oggetto remoto --> problema con i firewall

        this.port=port;
        this.serverName=serverName;
        this.mainServer=mainServer;
    }
    @Override
    public void sendRequest(NetworkRequest request) throws RemoteException {

    }
    @Override
    public void connect(String identifier ,VirtualViewRmi client) throws RemoteException {
        //more clients could invoke this
        //we create a rmiadapter and add him to rmi server clients
            ClientConnection rmiClient = new RmiClientAdapter(client);
            mainServer.addClient(identifier,rmiClient);
            System.out.println("Connected "+identifier);
    }

    public void start () throws RemoteException, UnknownHostException {

        //modifica: di default java usa l'hostname locale che non è raggiungibile esternamente
        //aggiunta anche l'eccezione UnknownHostException
        System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName,this);
    }



}
