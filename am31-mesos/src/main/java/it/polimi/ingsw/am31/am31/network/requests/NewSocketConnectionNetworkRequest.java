package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewSocketConnectionNetworkRequest extends NetworkRequest{

    @JsonCreator
    public NewSocketConnectionNetworkRequest(){
        super(RequestMethodsConstants.METHOD_NEW_CONNECTION);
    }
}
