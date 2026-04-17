package it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage;

import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class PlayerTribeUpdate extends UpdateMessage {

    private final List<String> tribeCardsIds;
    private final String playerId;

    public PlayerTribeUpdate(String playerId, List<String> tribeCardsIds){
        super(UpdateMethodsConstants.PLAYER_TRIBES_UPDATE_METHOD);
        this.playerId = playerId;
        this.tribeCardsIds = tribeCardsIds;
    }

    public String getPlayerId(){return this.playerId; }
    public List<String> getTribeCardsId(){return this.tribeCardsIds; }

}
