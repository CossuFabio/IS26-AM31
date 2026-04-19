package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

public class GameRoundStatusUpdate extends UpdateMessage {

    private final int roundNumber;
    private final RoundPhasesEnum phase;

    @JsonCreator
    public GameRoundStatusUpdate(@JsonProperty("roundNumber") int roundNumber,
                                 @JsonProperty("phase") RoundPhasesEnum phase){
        super(UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD);
        this.roundNumber = roundNumber;
        this.phase = phase;
    }

    public int getRoundNumber(){return this.roundNumber; }
    public RoundPhasesEnum getPhase(){return this.phase; }

}
