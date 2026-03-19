package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.Player;

public class BuilderDiscountHandlerDecorator extends SustainHandlerDecorator {

    public BuilderDiscountHandlerDecorator(ISustainHandler wrappedHandler) {super(wrappedHandler);}

    @Override
    public void handleSustain(Player player, int malus) {

    }
}
