package it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

public class PlayerScoresUpdate extends UpdateMessage {

    private final String playerId;
    private final Integer newPrestigePoints;
    private final Integer newFood;
    @JsonCreator
    public PlayerScoresUpdate(
            @JsonProperty("playerId") String playerId,
            @JsonProperty("newPrestigePoints") Integer newPrestigePoints,
            @JsonProperty("newFood") Integer newFood){
        super(UpdateMethodsConstants.PLAYER_SCORES_UPDATE_METHOD);
        this.playerId = playerId;
        this.newPrestigePoints = newPrestigePoints;
        this.newFood = newFood;
    }

    public String getPlayerId(){return this.playerId; }
    public Integer getNewPrestigePoints(){return this.newPrestigePoints;}
    public Integer getNewFood(){return this.newFood;}

    @Override
    protected boolean checkSpecificValidity(){
        return playerId != null && newPrestigePoints != null && newFood != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
