package it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;


import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class DisconnectNetworkRequest extends NetworkRequest {
    @JsonCreator
    public DisconnectNetworkRequest() {
        super(RequestMethodsConstants.METHOD_DISCONNECT);
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return true;
    }
}
