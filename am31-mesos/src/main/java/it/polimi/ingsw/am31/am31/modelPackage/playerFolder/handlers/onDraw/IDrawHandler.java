package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

/** Handler for player actions triggered when a card is drawn. */
public interface IDrawHandler {
    /**
     * Handles the draw of a card by the given player.
     *
     * @param player the player drawing the card
     * @param card   the card being drawn
     */
    void handleDraw(Player player, Card card);
}
