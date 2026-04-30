package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class PlayerBuildingsUpdate extends UpdateMessage {

    private final List<String> buildingCardsIds;
    private final String playerId;
    @JsonCreator
    public PlayerBuildingsUpdate(
            @JsonProperty ("playerId")String playerId,
            @JsonProperty ("buildingCardsIds")List<String> buildingCardsIds){
        super(UpdateMethodsConstants.PLAYER_BUILDINGS_UPDATE_METHOD);
        this.playerId = playerId;
        this.buildingCardsIds = buildingCardsIds;
    }

    public String getPlayerId(){return this.playerId; }
    public List<String> getBuildingCardsIds(){return this.buildingCardsIds; }

    @Override
    protected boolean checkSpecificValidity(){
        return this.buildingCardsIds!=null && !this.buildingCardsIds.contains(null) && playerId != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.HandleUpdateMessage(this);
    }


}
