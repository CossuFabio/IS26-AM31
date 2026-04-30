package it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.ClientConfig;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class NewServerConnectionRequest extends NetworkRequest {

    private String requestedUsername;


    @JsonCreator
    public NewServerConnectionRequest(@JsonProperty("requestedUsername") String requestedUsername){
        super(RequestMethodsConstants.METHOD_NEW_CONNECTION);
        this.requestedUsername = requestedUsername;
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return requestedUsername != null && !requestedUsername.equals(ClientConfig.UNREGISTERED_CLIENT_ID);
    }

    public String getRequestedUsername(){return requestedUsername;}

}
