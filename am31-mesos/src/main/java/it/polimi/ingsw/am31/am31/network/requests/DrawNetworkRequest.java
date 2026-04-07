package it.polimi.ingsw.am31.am31.network.requests;

import it.polimi.ingsw.am31.am31.controller.BoardRows;

public class DrawNetworkRequest extends NetworkRequest{

    private final String playerID;
    private final String cardID;
    private final BoardRows boardRows;

    public DrawNetworkRequest(String playerID, String cardId, BoardRows row){
        super("DRAW");
        this.playerID = playerID;
        this.cardID = cardId;
        this.boardRows = row;
    }

    public String getPlayerID(){return playerID;}
    public String getCardID(){return cardID;}
    public BoardRows getBoardRows(){return boardRows;}

}
