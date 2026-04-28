package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateVisitor;

public class GameStartUpdate extends UpdateMessage {

    @JsonCreator
    public GameStartUpdate(){
        super(UpdateMethodsConstants.GAME_START_UPDATE);
    }

    public void acceptVisit(UpdateVisitor updateVisitor) {
        updateVisitor.visit(this);
    }


    @Override
    protected boolean checkSpecificValidity() {
        return true;
    }
}

