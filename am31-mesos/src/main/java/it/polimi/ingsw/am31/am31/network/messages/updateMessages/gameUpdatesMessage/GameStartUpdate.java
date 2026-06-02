package it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateMethodsConstants;

/**
 * Notifies the client that the game has started
 */
public class GameStartUpdate extends UpdateMessage {

    @JsonCreator
    public GameStartUpdate(){
        super(UpdateMethodsConstants.GAME_START_UPDATE);
    }


    @Override
    protected boolean checkSpecificValidity() {
        return true;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }

}

