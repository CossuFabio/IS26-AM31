package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")


@JsonSubTypes({
        @JsonSubTypes.Type(value = DrawNetworkRequest.class, name = RequestMethodsConstants.METHOD_DRAW),
        @JsonSubTypes.Type(value = TotemNetworkRequest.class, name = RequestMethodsConstants.METHOD_PLACE_TOTEM),
        @JsonSubTypes.Type(value = JoinGameNetworkRequest.class, name = RequestMethodsConstants.METHOD_JOIN_GAME),
        @JsonSubTypes.Type(value = NewGameNetworkRequest.class, name =  RequestMethodsConstants.METHOD_NEW_GAME),
        @JsonSubTypes.Type(value = ShowLobbyNetworkRequest.class, name = RequestMethodsConstants.METHOD_SHOW_LOBBIES),
        @JsonSubTypes.Type(value = PingNetworkRequest.class, name = RequestMethodsConstants.PING),
        @JsonSubTypes.Type(value = NewServerConnectionRequest.class, name = RequestMethodsConstants.METHOD_NEW_CONNECTION)
})
public abstract class NetworkRequest{


    private final String type;
    private String playerID;

    protected NetworkRequest(String type){this.type = type;}

    @JsonIgnore
    public String getType(){return type;}


    public String getPlayerID(){return playerID;}

    //Will be called by the connection right before the invocation of the method that
    //sends the message to the server in the transport layer
    public void setPlayerID(String playerID){this.playerID = playerID; }

    public final boolean checkValidity(){
        return this.type != null && this.playerID != null && checkSpecificRequestValidity();
    }

    protected abstract boolean checkSpecificRequestValidity();

}
