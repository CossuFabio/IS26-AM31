package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.sustainEvent.ISustainDiscountCharacter;

public class EmptySustainDiscountCharacter implements ISustainDiscountCharacter {

    public EmptySustainDiscountCharacter() {}

    @Override
    public int getBonus(Player player) { return 0; }
}
