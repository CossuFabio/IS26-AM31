package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.handlers.sustainEvent.ISustainHandler;
import it.polimi.ingsw.am31.am31.handlers.sustainEvent.SustainHandlerDecorator;

public class InventorDiscountHandlerDecorator extends SustainHandlerDecorator {

    public InventorDiscountHandlerDecorator(ISustainHandler wrappedHandler) {super(wrappedHandler);}

    @Override
    public void handleSustain(Player player, int malus) {

    }
}
