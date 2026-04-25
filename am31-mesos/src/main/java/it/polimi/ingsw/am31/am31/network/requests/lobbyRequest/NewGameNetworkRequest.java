package it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class NewGameNetworkRequest extends NetworkRequest {
    private final Integer numPlayers;
    private final Color color;
    @JsonCreator
    public  NewGameNetworkRequest(
            @JsonProperty("numPlayers")int numPlayers,
            @JsonProperty("color") Color color)  {

        super(RequestMethodsConstants.METHOD_NEW_GAME);
        this.numPlayers = numPlayers;
        this.color = color;
    }

    public Integer getNumPlayers() {return numPlayers;}
    public Color getColor(){return color;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return numPlayers != null && color != null;
    }
}
