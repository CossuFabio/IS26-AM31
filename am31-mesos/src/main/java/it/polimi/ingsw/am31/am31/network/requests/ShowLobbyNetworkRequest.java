package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowLobbyNetworkRequest extends NetworkRequest{

    private final String identifier;
    @JsonCreator
    public ShowLobbyNetworkRequest( @JsonProperty("identifier")String identifier){
        super(RequestMethodsConstants.METHOD_SHOW_LOBBIES);
        this.identifier = identifier;
    }

    public String getIdentifier(){return identifier;}

}
