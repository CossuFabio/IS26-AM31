package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VirtualViewRmi extends Remote {
    //should have methods the server calls on its rmi clients
    void receiveUpdate(String data) throws RemoteException;
    void receiveMessage(String data) throws RemoteException;


    //Tolto anche qua, vedi motivazioni su VirtualView se serve
    //void setGameController(GameController controller) throws RemoteException;


}
