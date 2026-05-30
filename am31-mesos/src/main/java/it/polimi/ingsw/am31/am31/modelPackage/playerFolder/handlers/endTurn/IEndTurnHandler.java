package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Handler for player actions triggered when returning to the turn order tile at the end of the action phase. */
public interface IEndTurnHandler {
    /**
     * Resolves end-of-turn food and prestige changes for the given player.
     *
     * @param player     the player returning to the turn order tile
     * @param order      the turn order position the player placed their totem on (0-indexed)
     * @param numPlayers total number of players in the game
     */
    void handleEndTurn(Player player, int order, int numPlayers);
}
