package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateVisitor;

public class GameRoundStatusUpdate extends UpdateMessage {

    private final Integer roundNumber;
    private final RoundPhasesEnum phase;

    @JsonCreator
    public GameRoundStatusUpdate(@JsonProperty("roundNumber") Integer roundNumber,
                                 @JsonProperty("phase") RoundPhasesEnum phase){
        super(UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD);
        this.roundNumber = roundNumber;
        this.phase = phase;
    }

    public Integer getRoundNumber(){return this.roundNumber; }
    public RoundPhasesEnum getPhase(){return this.phase; }

    public void acceptVisit(UpdateVisitor updateVisitor) {
       updateVisitor.visit(this);
    }

    @Override
    protected boolean checkSpecificValidity(){
        return this.roundNumber != null && phase != null;
    }


}
