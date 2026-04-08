package it.polimi.ingsw.am31.am31.network.rmi.client;

import it.polimi.ingsw.am31.am31.network.ServerConnection;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualViewRmi, ServerConnection {

    private final VirtualServerRmi serverStub;
    private final String identifier;

    public RmiClient(String ip, int port, String identifier) throws RemoteException, NotBoundException {
        super();
        this.identifier = identifier;
        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,port);
        this.serverStub = (VirtualServerRmi) registry.lookup("MesosServer");
        //sends himself to server
        this.serverStub.connect(this.identifier,this);
    }
//    private void run() throws RemoteException {
//        //connects to the rmi server on server
//        this.serverStub.connect("", this);
//        this.runCli();
//    }
   public void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print("> ");
            // commands request
            String command = scan.next();
            if(command == "1") {
                //serverStub.showUpdate(); //example
            }
        }
    }
    @Override
    public void sendRequest(NetworkRequest request) {
        //sends request to server
        //virtualserver should accept networkrequests
    }

    @Override
    public void disconnect() {
        //disconnection, at the end of a game?
    }

    @Override
    public void receiveUpdate(Object data) {

    }
}
