package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.network.messages.errorMessage.ErrorCode;

public class WrongRoundPhaseException extends IllegalActionException {
    public WrongRoundPhaseException() {
        super("Wrong round phase!", ErrorCode.WRONG_ROUND_PHASE);
    }
}
