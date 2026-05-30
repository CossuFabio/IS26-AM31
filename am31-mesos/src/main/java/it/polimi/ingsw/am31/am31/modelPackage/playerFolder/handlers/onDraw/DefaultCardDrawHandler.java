package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;

/** Base draw handler. Activates the onPick effect of the drawn card. */
public class DefaultCardDrawHandler implements IDrawHandler{

    public DefaultCardDrawHandler(){}


    @Override
    public void handleDraw(Player player, Card card) {
        ((IPickable)card).onPick(player);
    }
}
