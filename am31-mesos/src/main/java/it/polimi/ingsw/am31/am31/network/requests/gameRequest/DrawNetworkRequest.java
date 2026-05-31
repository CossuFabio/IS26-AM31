package it.polimi.ingsw.am31.am31.network.requests.gameRequest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class DrawNetworkRequest extends NetworkRequest {

    public static final String METHOD = RequestMethodsConstants.METHOD_DRAW;

    private final String cardID;
    private final BoardRows boardRow;

    @JsonCreator
    public DrawNetworkRequest(
            @JsonProperty("cardID") String cardID,
            @JsonProperty("boardRows")BoardRows row){
        super(METHOD);
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