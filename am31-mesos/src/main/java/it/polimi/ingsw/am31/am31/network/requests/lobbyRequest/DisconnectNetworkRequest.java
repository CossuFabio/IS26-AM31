package it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;


import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class DisconnectNetworkRequest extends NetworkRequest {

    public static final String METHOD = RequestMethodsConstants.METHOD_DISCONNECT;


    @JsonCreator
    public DisconnectNetworkRequest() {
        super(METHOD);
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return true;
    }
}
