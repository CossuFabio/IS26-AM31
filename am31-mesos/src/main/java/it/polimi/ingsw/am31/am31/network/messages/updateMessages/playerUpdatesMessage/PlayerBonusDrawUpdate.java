package it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

/**
 * Updates the client with whether a player has a bonus draw available
 */
public class PlayerBonusDrawUpdate extends UpdateMessage {

    private final String playerNickname;
    private final boolean hasBonusDraw;

    @JsonCreator
    public PlayerBonusDrawUpdate(
            @JsonProperty("playerNickname") String playerNickname,
            @JsonProperty("hasBonusDraw") boolean hasBonusDraw){
        super(UpdateMethodsConstants.PLAYER_BONUS_DRAW_METHOD);

        this.playerNickname = playerNickname;
        this.hasBonusDraw = hasBonusDraw;
    }

    public String getPlayerNickname(){return playerNickname; }

    @JsonProperty("hasBonusDraw")
    public boolean hasBonusDraw(){return hasBonusDraw; }

    @Override
    protected boolean checkSpecificValidity() {
        return playerNickname != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor) {
        visitor.handleUpdateMessage(this);
    }
}
