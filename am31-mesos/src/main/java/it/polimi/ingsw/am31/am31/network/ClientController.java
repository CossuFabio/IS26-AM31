package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.PingNetworkRequest;

public class ClientController {
    private final VirtualServer connection;

    public ClientController(VirtualServer connection) {
        this.connection = connection;
    }
    public void ping() {
        Thread pingThread = new Thread (() -> {
            while(true) {
                try{
                    Thread.sleep(ClientConfig.CLIENT_HEARTBEAT_INTERVAL);
                    NetworkRequest ping = new PingNetworkRequest();
                    this.connection.sendRequest(ping);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    System.out.println("Server Error");
                    try {
                        this.connection.disconnect();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    break;
                }
            }

        });
        pingThread.setDaemon(true);
        pingThread.start();
    }
    public void sendRequest(NetworkRequest request) throws Exception {
        this.connection.sendRequest(request);
    }
}
