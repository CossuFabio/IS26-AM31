package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateVisitor;

public class GameRoundStatusUpdate extends UpdateMessage {

    private final Integer roundNumber;
    private final RoundPhasesEnum phase;
    private final int era;

    @JsonCreator
    public GameRoundStatusUpdate(@JsonProperty("roundNumber") Integer roundNumber,
                                 @JsonProperty("phase") RoundPhasesEnum phase,
                                 @JsonProperty("era") int era)
        {
        super(UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD);
        this.roundNumber = roundNumber;
        this.phase = phase;
        this.era = era;
    }

    public Integer getRoundNumber(){return this.roundNumber; }
    public RoundPhasesEnum getPhase(){return this.phase; }
    public int getEra () {return this.era;}


    @Override
    protected boolean checkSpecificValidity(){
        return this.roundNumber != null && phase != null;
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }
}
