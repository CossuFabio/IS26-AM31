package it.polimi.ingsw.am31.am31.network.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.controller.BoardRows;

public class DrawNetworkRequest extends NetworkRequest{

    private final String playerID;
    private final String cardID;
    private final BoardRows boardRow;

    @JsonCreator
    public DrawNetworkRequest(
            @JsonProperty("playerID")String playerID,
            @JsonProperty("cardID") String cardID,
            @JsonProperty("boardRow")BoardRows row){
        super(RequestMethodsConstants.METHOD_DRAW);
        this.playerID = playerID;
        this.cardID = cardID;
        this.boardRow = row;
    }

    public String getPlayerID(){return playerID;}
    public String getCardID(){return cardID;}
    public BoardRows getBoardRows(){return boardRow;}
    
}