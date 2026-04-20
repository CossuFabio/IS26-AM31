package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;

public class WrongRoundPhaseException extends IllegalActionException {
    public WrongRoundPhaseException() {
        super("Wrong round phase!");
    }
}
