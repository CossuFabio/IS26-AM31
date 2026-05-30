package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/** Bonus effect that discounts the food required in the sustain event. */
public interface ISustainDiscountCharacter {

    /**
     * Returns the food discount this specific bonus contributes for the given player.
     *
     * @param player the player being evaluated
     */
    int getBonus(Player player);

}
