package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

public interface IDrawHandler {
    void handleDraw(Player player, Card card);
}
