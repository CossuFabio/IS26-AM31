package it.polimi.ingsw.am31.am31.network.requests.transportLayerRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class NewServerConnectionRequest extends NetworkRequest {

    @JsonCreator
    public NewServerConnectionRequest(){
        super(RequestMethodsConstants.METHOD_NEW_CONNECTION);
    }

    @Override
    protected boolean checkSpecificRequestValidity(){
        //Nothing to verify
        return true;
    }

}
