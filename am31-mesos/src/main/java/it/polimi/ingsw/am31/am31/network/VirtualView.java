package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.rmi.RemoteException;
import java.util.List;

public interface VirtualView {
    //methods called by server on its clients, to send them updates and such
    void receiveUpdate(UpdateMessage data) throws Exception;
    void receiveMessage(String data) throws Exception;
    void receiveErrorMessage(ErrorMessage error);
    void updateLastTime();
    long getLastTime();
    void forceDisconnect();
    //Lo lascio scritto commentato ma andrebbe tolto: non si puo mandare un controller intero via rete
    //per due motivi:
    // 1- Stai dicendo al client che deve conoscere la logica di gioco e l'interfaccia del controller, ma il client deve rimanere
    //  ignorante rispetto alla logica del controller. + problemi di sicurezza ma in realta non dobbiamo occuparcene
    // 2- In socket non funziona perche deve avere l'oggetto game e non è il vero controller ma una ricostruzione locale.
    // In realta non funziona neanche in RMI perche quello che viene inviato è la serializzazione del controller, non uno stub che chiama metodi remoti
    //void setGameController(GameController controller) throws Exception;


}
