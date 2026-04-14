package it.polimi.ingsw.am31.am31.network.rmi.client;

import it.polimi.ingsw.am31.am31.exceptions.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.ServerConnection;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class RmiServerConnection extends UnicastRemoteObject implements  VirtualViewRmi, ServerConnection {

    private final VirtualServerRmi serverStub;
    private final String identifier;

    public RmiServerConnection(String ip, int port, String identifier) throws RemoteException, NotBoundException {
        //vecchia versione: super()

        //modifica: porta fissa per il callback
        super(port+1);

        this.identifier = identifier;
        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,port);
        this.serverStub = (VirtualServerRmi) registry.lookup("MesosServer");
        //sends himself to server
        this.serverStub.connect(this.identifier,this);
    }

   public void runCli() throws IOException, TooManyPlayersException {
        Scanner scan = new Scanner(System.in);
       System.out.print("Commands:\ncreateGame [nplayer]\nshowLobbies\njoinLobby [number of lobby] [totem's color]\n");
        while(true) {
            System.out.print("> ");
            // commands request
            String command = scan.next();
            if(command.equals("createGame") )
            {
                int nplayers = scan.nextInt();
                serverStub.createGame(nplayers);
            }
            if(command.equals("showLobbies"))
            {
                serverStub.showLobbies();
            }
            if(command.equals("joinLobby"))
            {
                int i = scan.nextInt();
                Color color = Color.valueOf(scan.next());
                serverStub.joinGameLobby(this.identifier ,color, i);
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
    public void receiveMessage (List<String> data) {

       for(String s: data)
           System.out.println(s);
    }
    @Override
    public void receiveUpdate (Object data) {}

}
