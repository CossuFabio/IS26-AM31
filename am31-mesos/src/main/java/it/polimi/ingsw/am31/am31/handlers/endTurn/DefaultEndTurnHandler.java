package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.TurnOrder;

public class DefaultEndTurnHandler implements IEndTurnHandler {

    public DefaultEndTurnHandler() {
    }

    @Override
    //By default, at the end of the turn the player gains/looses food based on which tile he is placed
    //All bonuses change based on number of players
    public void handleEndTurn(Player player, int playerOrder, int nPlayers) {
        if (nPlayers == 2) {
            if (playerOrder == 0)
                player.editFood(1);
            else {
                if (player.getFood() > 1)
                    player.editFood(-1);
                else
                    player.editPrestigePoints(-2);
            }
        }
        if (nPlayers == 3) {
            if (playerOrder == 0)
                player.editFood(2);
            if (playerOrder == 2) {
                if (player.getFood() > 1)
                    player.editFood(-1);
                else
                    player.editPrestigePoints(-2);
            }
        }
        if (nPlayers == 4) {
            if (playerOrder == 0)
                player.editFood(2);
            if (playerOrder == 1)
                player.editFood(1);
            if (playerOrder == 3) {
                if (player.getFood() > 1)
                    player.editFood(-1);
                else
                    player.editPrestigePoints(-2);
            }
        }
        if (nPlayers == 5) {
            if (playerOrder == 0)
                player.editFood(3);
            if (playerOrder == 1)
                player.editFood(1);
            if (playerOrder == 4) {
                if (player.getFood() > 1)
                    player.editFood(-1);
                else
                    player.editPrestigePoints(-2);
            }
        }
    }
}

