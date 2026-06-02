package it.polimi.ingsw.am31.am31.network.requests.lobbyRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

/** Request to join an existing game with a chosen color */
public class JoinGameNetworkRequest extends NetworkRequest {


    public static final String METHOD = RequestMethodsConstants.METHOD_JOIN_GAME;

    private final Color color;
    private final Integer gameID;


    @JsonCreator
    public JoinGameNetworkRequest(
            @JsonProperty("color")Color color,
            @JsonProperty("gameID")Integer gameID) {
        super(METHOD);
        this.color = color;
        this.gameID = gameID;
    }


    public Color getColor() {return color;}
    public Integer getGameID() {return gameID;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        //gameID validity is domain oriented
        return this.color != null && gameID != null;
    }


}
