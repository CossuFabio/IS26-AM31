package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.Server;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualServerRmi;
import it.polimi.ingsw.am31.am31.network.rmi.client.VirtualViewRmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RmiServer extends UnicastRemoteObject implements VirtualServerRmi {
    private List<VirtualViewRmi> clients;
    private int port;
    private Server mainServer;
    private String serverName;
    public RmiServer(String serverName,int port, Server mainServer) throws RemoteException {
        super();
        this.port=port;
        this.serverName=null;
        this.mainServer=mainServer;

    }
    public static void main(String[] args) throws RemoteException {


    }
    @Override
    public void showUpdate(){
        System.out.println("Received 1");
    }
    @Override
    public void reportErorr(String text){

    }

   // @Override
   // public void connect(VirtualViewRmi client) throws RemoteException {
    //    //more clients could invoke this
   //     synchronized (this.clients) {
  //          this.clients.add(client);
  //      }
 //   }
    @Override
    public RmiClientAdapter connect(String identifier ,VirtualViewRmi client) throws RemoteException {
        //more clients could invoke this

            RmiClientAdapter rmiClient = new RmiClientAdapter(client);
            return rmiClient;
        //some way to send it back
    }
    public void start () throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(serverName,this);
    }


}
