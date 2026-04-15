package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TotemNetworkRequest extends NetworkRequest {
    private final String playerID;
    private final String offerTrackID;

    @JsonCreator
    public TotemNetworkRequest(
            @JsonProperty("playerID")String playerID,
            @JsonProperty("offerTrackID")String offerTrackID){
        super(RequestMethodsConstants.METHOD_PLACE_TOTEM);
        this.playerID = playerID;
        this.offerTrackID = offerTrackID;
    }

    public String getPlayerID() {return playerID;}
    public String getOfferTrackID() {return offerTrackID;}
}
