package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.TurnOrder;

public interface IEndTurnHandler {
    void handleEndTurn(Player player, int order, int numPlayers);
}
