package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;

import java.rmi.RemoteException;
import java.util.List;

public interface VirtualView {
    //methods called by server on its clients, to send them updates and such
    void receiveUpdate(Object data) throws Exception;
    void setGameController(GameController controller) throws Exception;
    void receiveMessage(List<String> data) throws Exception;

}
