package it.polimi.ingsw.am31.am31.network.rmi.server;

import it.polimi.ingsw.am31.am31.network.ClientConnection;
import it.polimi.ingsw.am31.am31.network.rmi.client.VirtualViewRmi;

public class RmiClientAdapter implements ClientConnection {
    private final VirtualViewRmi client;
    public RmiClientAdapter (VirtualViewRmi client) {
        this.client = client;
    }

    @Override
    public void sendUpdate(Object data) {

    }

}
