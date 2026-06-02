package it.polimi.ingsw.am31.am31.network;


import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.SkipDrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;
import it.polimi.ingsw.am31.am31.view.eventsHandling.IEventBus;
import it.polimi.ingsw.am31.am31.view.eventsHandling.Subscribe;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.ConnectionLostEvent;
import it.polimi.ingsw.am31.am31.view.eventsHandling.events.SuccessRegistrationEvent;

/**
 * Client-side controller that handles the heartbeat to the server.
 * It acts as a server stub for the view and redirects the requests to the server via a {@link VirtualServer} sending DTO.
 * It also handles disconnections and sends heartbeat packages to the servers
 */
public class ClientController {

    private final VirtualServer connection;
    private volatile boolean connected = true;

    private String localPlayerUsername = ClientConfig.UNREGISTERED_CLIENT_ID;

    private volatile boolean usernameSet = false;
    private final IEventBus eventBus;

    public ClientController(VirtualServer connection, IEventBus eventBus) {
        this.connection = connection;
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    public void ping() {
        Thread pingThread = new Thread(() -> {
            while (connected) {
                try {
                    Thread.sleep(ClientConfig.CLIENT_HEARTBEAT_INTERVAL);
                    NetworkRequest ping = new PingNetworkRequest();
                    this.connection.sendRequest(ping);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    System.out.println("Server Error");
                    try {
                        disconnect();
                    } catch (Exception ignored) {}
                    eventBus.post(new ConnectionLostEvent());
                    break;
                }
            }

        });
        pingThread.setDaemon(true);
        pingThread.start();
    }

    public void disconnect() throws Exception {
        if (!connected) return;
        connected = false;
        this.connection.disconnect();
    }

    public boolean isConnected() {
        return connected;
    }

    public synchronized void setLocalPlayerUsername(String identifier) {
        if (!usernameSet) {
            usernameSet = true;
            this.localPlayerUsername = identifier;
            connection.setIdentifier(identifier);
            ping();
        }
    }

    public void requestServerConnection(String playerUsername) throws Exception {
        NetworkRequest req = new NewServerConnectionRequest(playerUsername);
        this.connection.sendRequest(req);
    }

    public void requestShowLobbies() throws Exception {
        NetworkRequest req = new ShowLobbyNetworkRequest();
        this.connection.sendRequest(req);
    }

    public void requestNewGame(int nPlayers, Color color) throws Exception {
        NetworkRequest req = new NewGameNetworkRequest(nPlayers, color);
        this.connection.sendRequest(req);
    }

    public void requestJoinGame(Color color, int gameId) throws Exception{
        NetworkRequest req = new JoinGameNetworkRequest(color, gameId);
        this.connection.sendRequest(req);
    }

    public void requestDraw(String cardId, BoardRows row) throws Exception {
        NetworkRequest req = new DrawNetworkRequest(cardId, row);
        this.connection.sendRequest(req);
    }

    public void requestSkip(BoardRows row) throws Exception {
        NetworkRequest req = new SkipDrawNetworkRequest(row);
        this.connection.sendRequest(req);
    }

    public void requestTotemPlacement(String totemId) throws Exception {
        NetworkRequest req = new TotemNetworkRequest(totemId);
        this.connection.sendRequest(req);
    }


    public synchronized String getLocalPlayerUsername() {
        return localPlayerUsername;
    }

    @Subscribe
    public void usernameAccepted(SuccessRegistrationEvent e) {
        setLocalPlayerUsername(e.getIdentifier());
    }

    public void setLocalNameTest() {
        localPlayerUsername = "test";
    }

    public IEventBus getEventBus() {
        return eventBus;
    }

    @Subscribe
    public void connectionLost(ConnectionLostEvent e) {
        try {
            this.disconnect();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
