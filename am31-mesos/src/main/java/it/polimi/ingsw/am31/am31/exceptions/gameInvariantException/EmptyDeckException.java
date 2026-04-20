package it.polimi.ingsw.am31.am31.exceptions.gameInvariantException;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;

public class EmptyDeckException extends GameInvariantException {
    public EmptyDeckException() {
        super("Deck is empty!");
    }
}
