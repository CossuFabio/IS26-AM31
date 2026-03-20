package it.polimi.ingsw.am31.am31.Buildings;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.handlers.paintEvent.BonusPaintHandlerDecorator;

public class BonusPaintBuilding{

    public void activateEffect(Player player) {
        //This way you give the constructor address as function parameter
        player.addPaintEffect(BonusPaintHandlerDecorator::new);
    }
}
