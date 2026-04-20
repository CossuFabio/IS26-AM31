package it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class PlayerBuildingsUpdate extends UpdateMessage {

    private final List<String> buildingCardsIds;
    private final String playerId;
    @JsonCreator
    public PlayerBuildingsUpdate(
            @JsonProperty String playerId,
            @JsonProperty List<String> buildingCardsIds){
        super(UpdateMethodsConstants.PLAYER_BUILDINGS_UPDATE_METHOD);
        this.playerId = playerId;
        this.buildingCardsIds = buildingCardsIds;
    }

    public String getPlayerId(){return this.playerId; }
    public List<String> getBuildingCardsIds(){return this.buildingCardsIds; }

}
