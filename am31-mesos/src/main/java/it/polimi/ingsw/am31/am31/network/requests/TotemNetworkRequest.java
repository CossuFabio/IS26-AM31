package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TotemNetworkRequest extends NetworkRequest {

    private final String offerTrackID;

    @JsonCreator
    public TotemNetworkRequest(
            @JsonProperty("offerTrackID")String offerTrackID){

        super(RequestMethodsConstants.METHOD_PLACE_TOTEM);
        this.offerTrackID = offerTrackID;

    }

    public String getOfferTrackID() {return offerTrackID;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return offerTrackID != null;
    }

}
