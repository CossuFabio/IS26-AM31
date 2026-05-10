package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class DefaultEndTurnHandler implements IEndTurnHandler {


    public DefaultEndTurnHandler() {}

    @Override
    //By default, at the end of the turn the player gains/loses food based on which tile he is placed
    //All bonuses change based on number of players
    //We prefer to put here this logic, leaving these magic numbers, rather than creating a more complex logic that
    //implements it
    public void handleEndTurn(Player player, int playerOrder, int nPlayers) {
        if(playerOrder == 0){
            player.editFood(1);
            if(nPlayers > 2)
                player.editFood(1);
            if(nPlayers > 4)
                player.editFood(1);
        }
        if(playerOrder == 1 && nPlayers > 3)
            player.editFood(1);

        if(playerOrder == (nPlayers-1)){
            if (player.getFood() >= 1)
                player.editFood(-1);
            else
                player.editPrestigePoints(-2);
        }
    }
}


