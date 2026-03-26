package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public interface IEndTurnHandler {
    void handleEndTurn(Player player, int order, int numPlayers);
}
