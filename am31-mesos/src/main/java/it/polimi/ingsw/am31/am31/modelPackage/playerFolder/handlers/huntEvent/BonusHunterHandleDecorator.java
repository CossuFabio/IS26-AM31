package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class BonusHunterHandleDecorator extends HuntHandlerDecorator{

    public BonusHunterHandleDecorator(IHuntHandler wrappedHandler){super(wrappedHandler);}

    @Override

    public void handleHunt(Player player, int food, int prestigePoints) {
        //Code
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));

        //+1 food and +1 prestige for each hunter in tribe
        player.editFood(visitor.getHunters());
        player.editPrestigePoints(visitor.getHunters());

        wrappedHandler.handleHunt( player,  food,  prestigePoints);
    }
}
