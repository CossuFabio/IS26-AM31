package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateVisitor;
import it.polimi.ingsw.am31.am31.view.LocalGameState;

public class GameStartUpdate extends UpdateMessage {

    private final LocalGameState newGame;
    @JsonCreator
    public GameStartUpdate(LocalGameState newGame){
        super(UpdateMethodsConstants.GAME_START_UPDATE);
        this.newGame = newGame;
    }
    public LocalGameState getNewGame(){
        return newGame;
    }

    public void acceptVisit(UpdateVisitor updateVisitor) {
        updateVisitor.visit(this);
    }
}

