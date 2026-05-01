package it.polimi.ingsw.am31.am31.network;

import com.google.common.eventbus.Subscribe;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;

public class ClientController {
    private final VirtualServer connection;
    private boolean connected = true;

    private String localPlayerUsername = ClientConfig.UNREGISTERED_CLIENT_ID;

    private volatile boolean usernameSet = false;
    private final IEventBus eventBus;

    public ClientController(VirtualServer connection, IEventBus eventBus) {
        this.connection = connection;
        this.eventBus = eventBus;
        eventBus.register(this);
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

    @Subscribe
    public void usernameAccepted(SuccessRegistrationEvent e){
        setLocalPlayerUsername(e.getIdentifier());
    }

}
