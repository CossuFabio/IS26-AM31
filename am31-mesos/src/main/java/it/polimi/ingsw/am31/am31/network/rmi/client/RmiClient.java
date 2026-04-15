package it.polimi.ingsw.am31.am31.network.rmi.client;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class RmiClient extends UnicastRemoteObject implements VirtualServer, VirtualViewRmi {
    //extends both, to allow callback from server

    private final VirtualServerRmi serverStub;
    private final String identifier;
    private VirtualViewRmi clientStub;


    public RmiClient(String ip, int port, String identifier) throws RemoteException, NotBoundException {
       super(port+1);

        this.identifier = identifier;
        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,port);
        this.serverStub = (VirtualServerRmi) registry.lookup("MesosServer");
        //sends himself to server
        this.serverStub.connect(this.identifier,this);


    }
    @Override
    public void sendRequest(NetworkRequest request) throws RemoteException {
        serverStub.sendRequest(RequestsMapper.serialize(request));//
    }

    @Override
    public void receiveMessage (List<String> data) throws RemoteException {
        for(String s: data)
            System.out.println(s);
    }
    @Override
    public void receiveUpdate (Object data) {

    }
    @Override
    public void setGameController(GameController controller) {

    }


    public void connect(String identifier, VirtualViewRmi clientStub) throws RemoteException{


    }
    @Override
    public void disconnect() throws RemoteException{

    }
}
