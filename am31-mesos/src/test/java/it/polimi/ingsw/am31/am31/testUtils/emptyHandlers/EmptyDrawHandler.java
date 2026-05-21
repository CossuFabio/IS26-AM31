package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw.IDrawHandler;

public class EmptyDrawHandler implements IDrawHandler {

    public EmptyDrawHandler() {}

    @Override
    public void handleDraw(Player player, Card card) {}
}
