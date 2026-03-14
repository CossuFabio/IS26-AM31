package it.polimi.ingsw.am31.am31.handlers.onDraw;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.cards.Card;

public class GeneralAdditionalFoodDecorator extends CardDrawHandlerDecorator{

    public GeneralAdditionalFoodDecorator(IDrawHandler wrappedHandler){super(wrappedHandler); }


    @Override
    //TO-DO: implement this
    public void handleDraw(Player player, Card card) {

        //Idea: keep counter for all types, increment by one the type using
        //visitor pattern to increment the correct type
        //check if all are zeroes: if so, give bonus food then decrement all counter by one (or to zero), else do nothing
        wrappedHandler.handleDraw(player, card);
    }
}
