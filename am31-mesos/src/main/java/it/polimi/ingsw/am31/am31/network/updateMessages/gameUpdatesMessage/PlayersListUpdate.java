package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class PlayersListUpdate extends UpdateMessage {

    private final List<String> playersList;

    public PlayersListUpdate(List<String> playersList){
        super(UpdateMethodsConstants.GAME_PLAYERS_LIST_UPDATE_METHOD);
        this.playersList = playersList;
    }

    public List<String> getPlayersList() {
        return playersList;
    }
}
