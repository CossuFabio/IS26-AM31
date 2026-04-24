package it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class NewGameNetworkRequest extends NetworkRequest {
    private Integer numPlayers;

    @JsonCreator
    public  NewGameNetworkRequest(
            @JsonProperty("numPlayers")int numPlayers) {
        super(RequestMethodsConstants.METHOD_NEW_GAME);
        this.numPlayers = numPlayers;
    }

    public Integer getNumPlayers() {return numPlayers;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return numPlayers != null;
    }
}
