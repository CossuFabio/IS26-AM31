package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShowLobbyNetworkRequest extends NetworkRequest{


    @JsonCreator
    public ShowLobbyNetworkRequest(){
        super(RequestMethodsConstants.METHOD_SHOW_LOBBIES);
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return true;
    }

}
