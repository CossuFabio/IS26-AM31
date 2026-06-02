package it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

/** Heartbeat request sent periodically by the client to keep the connection alive */
public class PingNetworkRequest extends NetworkRequest {

    public static final String METHOD = RequestMethodsConstants.PING;

    @JsonCreator
    public PingNetworkRequest() {
        super(METHOD);
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return true;
    }

}
