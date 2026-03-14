package it.polimi.ingsw.am31.am31.handlers.onDraw;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.cards.Card;

public interface IDrawHandler {
    void handleDraw(Player player, Card card);
}
