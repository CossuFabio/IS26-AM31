package it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class ShowLobbyNetworkRequest extends NetworkRequest {


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
