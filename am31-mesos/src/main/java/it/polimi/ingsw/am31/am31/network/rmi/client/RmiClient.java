package it.polimi.ingsw.am31.am31.network.rmi.client;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.ServerConfig;
import it.polimi.ingsw.am31.am31.network.VirtualServer;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessageMapper;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequestFactory;
import it.polimi.ingsw.am31.am31.network.requests.RequestsMapper;
import it.polimi.ingsw.am31.am31.network.rmi.server.VirtualViewRmi;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMapper;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class RmiClient extends UnicastRemoteObject implements VirtualServer, VirtualViewRmi {
    //extends both, to allow callback from server

    private final VirtualServerRmi serverStub;
    private final String identifier;
    private VirtualViewRmi clientStub;
    private LocalGameState gameState;

    public RmiClient(String ip, int port, String identifier) throws RemoteException, NotBoundException {

        super(port);

        this.identifier = identifier;
        //connects to registry
        Registry registry = LocateRegistry.getRegistry(ip,ServerConfig.SERVER_PORT_RMI);
        this.serverStub = (VirtualServerRmi) registry.lookup(ServerConfig.SERVER_NAME);
        //sends himself to RMI server
        this.serverStub.connect(this.identifier,this);

        //trying to register with specified id
        sendRequest(NetworkRequestFactory.createNewServerConnectionRequest());


    }
    @Override
    public void sendRequest(NetworkRequest request) throws RemoteException {
        request.setPlayerID(this.identifier);
        serverStub.sendRequest(RequestsMapper.serialize(request), this);
    }

    @Override
    public void receiveMessage (String data) throws RemoteException {
        System.out.println(data);
    }

    @Override
    public void receiveErrorMessage(String errorMessageString) throws RemoteException {
        ErrorMessage errorMessage = ErrorMessageMapper.deserialize(errorMessageString);
        System.out.println(errorMessage.getMessage());
    }


    @Override
    public void receiveUpdate (String updateMessage) {
        //TODO receival of all Updates -> call on the updater
        UpdateMessage message = UpdateMapper.deserialize(updateMessage);
        StateUpdater updater = new StateUpdater(gameState);
        if(message == null || !message.checkValidity()) return;
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_SHOW_LOBBY_UPDATE_METHOD)){
            ShowLobbyUpdate lobbyUpdate = (ShowLobbyUpdate) message;
            updater.HandleUpdateMessage(lobbyUpdate);
         }
        if (message.getUpdateType().equals(UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD)) {
            GameRoundStatusUpdate gameRoundUpdate = (GameRoundStatusUpdate) message;
            updater.HandleUpdateMessage(gameRoundUpdate);
        }
        if (message.getUpdateType().equals(UpdateMethodsConstants.GAME_START_UPDATE)){
            GameStartUpdate gameStartUpdate = (GameStartUpdate) message;
            updater.HandleUpdateMessage(gameStartUpdate);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.BOARD_CARDLINE_UPDATE_METHOD)) {
            CardLineUpdate update = (CardLineUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.BOARD_OFFERCARD_UPDATE_METHOD)) {
            OfferTrackUpdate update = (OfferTrackUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD)) {
            TurnOrderUpdate update = (TurnOrderUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_PLAYERS_LIST_UPDATE_METHOD)) {
            PlayersListUpdate update = (PlayersListUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.PLAYER_SCORES_UPDATE_METHOD)) {
            PlayerScoresUpdate update = (PlayerScoresUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_CRASHED_METHOD)) {
            GameCrashUpdate update = (GameCrashUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.PLAYER_BUILDINGS_UPDATE_METHOD)) {
            PlayerBuildingsUpdate update = (PlayerBuildingsUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.PLAYER_TRIBES_UPDATE_METHOD)) {
            PlayerTribeUpdate update = (PlayerTribeUpdate) message;
            updater.HandleUpdateMessage(update);
        }
  //      if(message.getUpdateType().equals(UpdateMethodsConstants.))
    }


    @Override
    public void disconnect() throws RemoteException{
        try {
            serverStub.disconnect(this.identifier);
            UnicastRemoteObject.unexportObject(this, true);
        } catch (Exception e) {
            System.err.println("Disconnection failed: " + e.getMessage());
        }
    }

    public void setGameState(LocalGameState gameState) {
        this.gameState = gameState;
    }

}
