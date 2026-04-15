package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGameNetworkRequest extends NetworkRequest{
    private int nplayers;

    @JsonCreator
    public  NewGameNetworkRequest(
            @JsonProperty("nplayers")int nplayers) {
        super(RequestMethodsConstants.METHOD_NEW_GAME);
        this.nplayers = nplayers;
    }

    public int getnplayers() {return nplayers;}
}
