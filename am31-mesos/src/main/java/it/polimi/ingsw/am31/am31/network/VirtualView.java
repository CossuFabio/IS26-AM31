package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;

public interface VirtualView {
    //methods called by server on its clients, to send them updates and such
    void receiveUpdate(UpdateMessage data) throws Exception;
    void receiveErrorMessage(ErrorMessage error);


    void updateLastTime();
    long getLastTime();
    void forceDisconnect();

}
