package it.polimi.ingsw.am31.am31.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

public class DefaultHuntHandler implements IHuntHandler{

    public DefaultHuntHandler(){}

    @Override
    public void handleHunt(Player player, int food, int prestigePoints) {
        CountVisitor countVisitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(countVisitor));
        int bonusFood = countVisitor.getHunters()*food;
        int bonusPP = countVisitor.getHunters()*prestigePoints;
        player.editPrestigePoints(bonusPP);
        player.editFood(bonusFood);
    }
}
