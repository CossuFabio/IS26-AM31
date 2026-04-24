package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class PlayersListUpdate extends UpdateMessage {

    private final List<String> playersList;

    @JsonCreator
    public PlayersListUpdate(@JsonProperty("playersList") List<String> playersList){
        super(UpdateMethodsConstants.GAME_PLAYERS_LIST_UPDATE_METHOD);
        this.playersList = playersList;
    }

    public List<String> getPlayersList() {
        return playersList;
    }


    @Override
    protected boolean checkSpecificValidity() {
        return playersList != null && !playersList.contains(null);
    }
}
