package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGameNetworkRequest extends NetworkRequest{
    private Integer nPlayers;

    @JsonCreator
    public  NewGameNetworkRequest(
            @JsonProperty("nPlayers")int nPlayers) {
        super(RequestMethodsConstants.METHOD_NEW_GAME);
        this.nPlayers = nPlayers;
    }

    public Integer nPlayers() {return nPlayers;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return nPlayers != null;
    }
}
