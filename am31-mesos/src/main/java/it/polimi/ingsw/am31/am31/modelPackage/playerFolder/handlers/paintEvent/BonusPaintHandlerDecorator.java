package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class BonusPaintHandlerDecorator extends PaintHandlerDecorator{

    public BonusPaintHandlerDecorator(IPaintHandler wrappedHandler){super(wrappedHandler); }

    @Override
    public void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editFood(visitor.getArtists());
        wrappedHandler.handlePaint(player, minimumArtistsNumber, prestigePointsBonus, prestigePointsMalus);
    }
}
