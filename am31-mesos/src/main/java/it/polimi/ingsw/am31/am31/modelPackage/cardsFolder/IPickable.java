package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder;

import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidPickException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/**
 * Implemented by cards that can be drawn by a player from the board.
 */
public interface IPickable {

    /**
     * Triggers the card's immediate draw effect.
     *
     * @param player the player drawing the card
     */
    void onPick(Player player);

    /**
     * Validates that the player meets the requirements to pick this card.
     *
     * @param player the player attempting to pick
     * @throws InvalidPickException if the player cannot pick this card
     */
    default void canPick(Player player) throws InvalidPickException {}

    /**
     * Places the card in the correct hand (tribe or buildings).
     *
     * @param player the player receiving the card
     */
    void addToPlayer(Player player);
}
