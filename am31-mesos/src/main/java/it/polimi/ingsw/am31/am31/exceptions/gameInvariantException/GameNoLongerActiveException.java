package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

public class GameNoLongerActiveException extends GameInvariantException {
    public GameNoLongerActiveException() {
        super("Game no longer active!");
    }
}
