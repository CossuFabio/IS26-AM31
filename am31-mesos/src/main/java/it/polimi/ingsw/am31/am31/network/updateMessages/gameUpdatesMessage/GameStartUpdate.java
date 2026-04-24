package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateVisitor;
import it.polimi.ingsw.am31.am31.view.LocalGameState;

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

