package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")


@JsonSubTypes({
        @JsonSubTypes.Type(value = DrawNetworkRequest.class, name = RequestMethodsConstants.METHOD_DRAW),
        @JsonSubTypes.Type(value = TotemNetworkRequest.class, name = RequestMethodsConstants.METHOD_PLACE_TOTEM),
        @JsonSubTypes.Type(value = JoinNetworkRequest.class, name = RequestMethodsConstants.METHOD_JOIN_GAME),
        @JsonSubTypes.Type(value = NewGameNetworkRequest.class, name =  RequestMethodsConstants.METHOD_NEW_GAME),
        @JsonSubTypes.Type(value = ShowLobbyNetworkRequest.class, name = RequestMethodsConstants.METHOD_SHOW_LOBBIES),
})
public abstract class NetworkRequest{


    private final String type;
    protected NetworkRequest(String type){this.type = type;}

    @JsonIgnore
    public String getType(){return type;}


}
