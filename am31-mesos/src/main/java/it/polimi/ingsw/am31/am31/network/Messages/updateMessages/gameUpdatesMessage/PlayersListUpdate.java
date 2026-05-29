package it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class PlayersListUpdate extends UpdateMessage {

    private final List<PlayerMessage> playersList;


    @JsonCreator
    public PlayersListUpdate(@JsonProperty("playersList") List<PlayerMessage> playersList ){
        super(UpdateMethodsConstants.GAME_PLAYERS_LIST_UPDATE_METHOD);
        this.playersList = playersList;
    }

    public List<PlayerMessage> getPlayersList() {
        return playersList;
    }


    @Override
    protected boolean checkSpecificValidity() {
        return playersList != null && !playersList.contains(null);
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}
