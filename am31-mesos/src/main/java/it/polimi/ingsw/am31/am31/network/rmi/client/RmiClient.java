package it.polimi.ingsw.am31.am31.network.rmi.client;

import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualServerRmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualViewRmi {
    final VirtualServerRmi server;

    public RmiClient(VirtualServerRmi server) throws RemoteException {
        super();
        this.server=server;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        final String serverName ="MesosServer"; //Mesos
        Registry registry = LocateRegistry.getRegistry(args[0],1100);
        VirtualServerRmi server = (VirtualServerRmi) registry.lookup(serverName);

        new RmiClient(server).run();
    }

    private void run() throws RemoteException {
        //connects to the rmi server on server
        this.server.connect("", this);
        this.runCli();
    }
    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print("> ");
            // commands request
            String command = scan.next();
            if(command == "1") {
                server.showUpdate(); //example
            }
        }
    }
}
