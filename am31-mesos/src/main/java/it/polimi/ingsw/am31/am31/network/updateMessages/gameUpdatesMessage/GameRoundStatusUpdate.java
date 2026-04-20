package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateVisitor;

public class GameRoundStatusUpdate extends UpdateMessage {

    private final int roundNumber;
    private final RoundPhasesEnum phase;

    public GameRoundStatusUpdate(int roundNumber, RoundPhasesEnum phase){
        super(UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD);
        this.roundNumber = roundNumber;
        this.phase = phase;
    }

    public int getRoundNumber(){return this.roundNumber; }
    public RoundPhasesEnum getPhase(){return this.phase; }

    public void acceptVisit(UpdateVisitor updateVisitor) {
       updateVisitor.visit(this);
    }
}
