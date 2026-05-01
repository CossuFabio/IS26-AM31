package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateVisitor;

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

