package it.polimi.ingsw.am31.am31.network.requests.gameRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class DrawNetworkRequest extends NetworkRequest {


    private final String cardID;
    private final BoardRows boardRow;

    @JsonCreator
    public DrawNetworkRequest(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("boardRow")BoardRows row){
        super(RequestMethodsConstants.METHOD_DRAW);
        this.cardID = cardID;
        this.boardRow = row;
    }


    public String getCardID(){return cardID;}
    public BoardRows getBoardRows(){return boardRow;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return this.cardID != null && boardRow != null;
    }

}