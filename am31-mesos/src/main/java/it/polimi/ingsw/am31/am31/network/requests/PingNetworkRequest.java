package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PingNetworkRequest extends NetworkRequest {
    @JsonCreator
    public PingNetworkRequest() {
        super(RequestMethodsConstants.PING);
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return true;
    }

}
