package it.polimi.ingsw.am31.am31.handlers.endTurn;

import it.polimi.ingsw.am31.am31.Player;

public class FoodEndTurnDecorator extends EndTurnHandlerDecorator{

    public FoodEndTurnDecorator(IEndTurnHandler wrappedHandler){super(wrappedHandler);}

    @Override
    //TO-DO: implement this
    public void handleEndTurn(Player player) {
        //Code
        wrappedHandler.handleEndTurn(player);
    }
}
