package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;

import java.rmi.RemoteException;
import java.util.List;

public interface ClientConnection {
    //methods called by server on its clients, to send them updates and such
    public void sendUpdate(Object data);
    public void setGameController(GameController controller);
    public void receiveMessage(List<String> data) throws RemoteException;
}


