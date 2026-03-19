package it.polimi.ingsw.am31.am31.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.Player;

public class BonusHunterHandleDecorator extends HuntHandlerDecorator{

    public BonusHunterHandleDecorator(IHuntHandler wrappedHandler){super(wrappedHandler);}

    @Override
    //TO-DO: implement this
    public void handleHunt(Player player, int food, int prestigePoints) {
        //Code

        wrappedHandler.handleHunt( player,  food,  prestigePoints);
    }
}
