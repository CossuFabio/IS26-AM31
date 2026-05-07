package it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.ClientConfig;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class NewServerConnectionRequest extends NetworkRequest {

    private final String requestedUsername;
    public static final String METHOD = RequestMethodsConstants.METHOD_NEW_CONNECTION;

    @JsonCreator
    public NewServerConnectionRequest(@JsonProperty("requestedUsername") String requestedUsername){
        super(METHOD);
        this.requestedUsername = requestedUsername;
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return requestedUsername != null && !requestedUsername.equals(ClientConfig.UNREGISTERED_CLIENT_ID);
    }

    public String getRequestedUsername(){return requestedUsername;}

}
