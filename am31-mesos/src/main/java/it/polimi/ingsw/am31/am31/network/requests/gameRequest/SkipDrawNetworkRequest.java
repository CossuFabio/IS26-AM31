package it.polimi.ingsw.am31.am31.network.requests.gameRequest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;

public class SkipDrawNetworkRequest extends NetworkRequest {

    public static final String METHOD = RequestMethodsConstants.METHOD_SKIP_DRAW;

    private final BoardRows boardRow;

    @JsonCreator
    public SkipDrawNetworkRequest(@JsonProperty("boardRow") BoardRows boardRow){
        super(METHOD);
        this.boardRow = boardRow;
    }

    public BoardRows getBoardRow(){return boardRow;}

    @Override
    protected boolean checkSpecificRequestValidity(){
        return boardRow != null;
    }
}
