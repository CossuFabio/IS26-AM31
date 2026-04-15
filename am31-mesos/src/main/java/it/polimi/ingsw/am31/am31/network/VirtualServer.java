package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;

public interface VirtualServer {

    //interface used clientside
    void sendRequest(NetworkRequest request) throws Exception;
    void disconnect() throws Exception;


}
