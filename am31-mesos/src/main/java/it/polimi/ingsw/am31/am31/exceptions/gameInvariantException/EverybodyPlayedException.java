package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

public class EverybodyPlayedException extends GameInvariantException {
    public EverybodyPlayedException() {
        super("No more players");
    }
}
