package it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException;

import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
//TODO RENAME TO InvalidPlayersNumberException with refactor
public class InvalidPlayersNumber extends LobbyException {
    public InvalidPlayersNumber() {
        super("Player number must be between " + GameConstants.MIN_PLAYERS + " and " + GameConstants.MAX_PLAYERS);
    }
}
