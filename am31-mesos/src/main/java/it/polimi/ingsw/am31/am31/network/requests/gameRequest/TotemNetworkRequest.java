package it.polimi.ingsw.am31.am31.network.requests.gameRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class TotemNetworkRequest extends NetworkRequest {

    public static final String METHOD = RequestMethodsConstants.METHOD_PLACE_TOTEM;

    private final String offerTrackID;

    @JsonCreator
    public TotemNetworkRequest(
            @JsonProperty("offerTrackID")String offerTrackID){

        super(METHOD);
        this.offerTrackID = offerTrackID;

    }

    public String getOfferTrackID() {return offerTrackID;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return offerTrackID != null;
    }

}
