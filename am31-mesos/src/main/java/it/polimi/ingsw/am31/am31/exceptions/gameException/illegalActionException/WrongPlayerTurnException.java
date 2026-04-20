package it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException;

import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;

public class WrongPlayerTurnException extends IllegalActionException {
    public WrongPlayerTurnException() {
        super("Wrong turn!");
    }
}
