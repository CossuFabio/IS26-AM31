package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

public class JoinNetworkRequest extends NetworkRequest {

    private final Color color;
    private final int gameID;


    //TODO: Finish this
    @JsonCreator
    public JoinNetworkRequest(
            @JsonProperty("color")Color color,
            @JsonProperty("gameID")int gameID) {
        super(RequestMethodsConstants.METHOD_JOIN_GAME);
        this.color = color;
        this.gameID = gameID;
    }


    public Color getColor() {return color;}
    public int getGameID() {return gameID;}
}
