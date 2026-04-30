package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;

public class ClientController {
    private final VirtualServer connection;
    private boolean connected = true;

    private String localPlayerUsername = ClientConfig.UNREGISTERED_CLIENT_ID;

    private volatile boolean usernameSet = false;


    public ClientController(VirtualServer connection) {
        this.connection = connection;
    }
    public void ping() {
        Thread pingThread = new Thread (() -> {
            while(connected) {
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

    public void disconnect() throws Exception {
        connected = false;
        this.connection.disconnect();
    }

    public boolean isConnected() {
        return connected;
    }

    public synchronized void setLocalPlayerUsername(String identifier){
        if(!usernameSet){
            usernameSet = true;
            this.localPlayerUsername = identifier;
            connection.setIdentifier(identifier);
            ping();
        }
    }

    public synchronized String getLocalPlayerUsername(){
        return localPlayerUsername;
    }

}
