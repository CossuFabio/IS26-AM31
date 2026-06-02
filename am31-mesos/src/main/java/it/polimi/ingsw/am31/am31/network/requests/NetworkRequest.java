package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.SkipDrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.DisconnectNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.NewServerConnectionRequest;
import it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest.PingNetworkRequest;



/**
 * Data Transfer Object sent from the Client to the Server.
 * This abstract class is used for both game-related requests and network-related requests (like the server registration request)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DisconnectNetworkRequest.class, name = RequestMethodsConstants.METHOD_DISCONNECT),
        @JsonSubTypes.Type(value = DrawNetworkRequest.class, name = RequestMethodsConstants.METHOD_DRAW),
        @JsonSubTypes.Type(value = TotemNetworkRequest.class, name = RequestMethodsConstants.METHOD_PLACE_TOTEM),
        @JsonSubTypes.Type(value = JoinGameNetworkRequest.class, name = RequestMethodsConstants.METHOD_JOIN_GAME),
        @JsonSubTypes.Type(value = NewGameNetworkRequest.class, name =  RequestMethodsConstants.METHOD_NEW_GAME),
        @JsonSubTypes.Type(value = ShowLobbyNetworkRequest.class, name = RequestMethodsConstants.METHOD_SHOW_LOBBIES),
        @JsonSubTypes.Type(value = PingNetworkRequest.class, name = RequestMethodsConstants.PING),
        @JsonSubTypes.Type(value = NewServerConnectionRequest.class, name = RequestMethodsConstants.METHOD_NEW_CONNECTION),
        @JsonSubTypes.Type(value = SkipDrawNetworkRequest.class, name = RequestMethodsConstants.METHOD_SKIP_DRAW)
})
public abstract class NetworkRequest{


    private final String type;
    private String playerID;

    protected NetworkRequest(String type){this.type = type;}


    /**
     * @return the corresponding request method
     */
    @JsonIgnore
    public String getType(){return type;}

    /**
     * @return the player id of the sender
     */
    public String getPlayerID(){return playerID;}


    /**
     * @param playerID attach the sender player id before sending
     */
    public void setPlayerID(String playerID){this.playerID = playerID; }

    /**
     * @return true if the integrity check of the internal values is passed.
     * Only checks for null values.
     */
    public final boolean checkValidity(){
        return this.type != null && this.playerID != null && checkSpecificRequestValidity();
    }

    /**
     * This method is used for the template pattern:subclasses must return {@code true} only if all their own fields are non-null.
     * Forces the implementation on subclasses.
     * @return true if the subtype-specific fields pass the null check
     */
    protected abstract boolean checkSpecificRequestValidity();

}
