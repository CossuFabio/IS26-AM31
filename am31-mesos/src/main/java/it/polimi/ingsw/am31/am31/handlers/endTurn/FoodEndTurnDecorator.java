package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.Player;

public class FoodEndTurnDecorator extends EndTurnHandlerDecorator{

    public FoodEndTurnDecorator(IEndTurnHandler wrappedHandler){super(wrappedHandler);}

    @Override
    //gets +1 additional food if totem is on first places of Turn Order
    public void handleEndTurn(Player player, int turnOrder, int nPlayers) {
        if(nPlayers > 3 && ((turnOrder == 0)||(turnOrder == 1)))
            player.editFood(1);
        else if ((nPlayers == 2 || nPlayers == 3) && turnOrder == 0)
            player.editFood(1);
        wrappedHandler.handleEndTurn(player, turnOrder, nPlayers);
    }
}
