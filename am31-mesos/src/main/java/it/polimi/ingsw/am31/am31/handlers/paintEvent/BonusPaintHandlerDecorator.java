package it.polimi.ingsw.am31.am31.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.Player;

public class BonusPaintHandlerDecorator extends PaintHandlerDecorator{

    public BonusPaintHandlerDecorator(IPaintHandler wrappedHandler){super(wrappedHandler); }

    @Override
    //TO-DO: implement
    public void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus) {
        //Do something
        wrappedHandler.handlePaint(player, minimumArtistsNumber, prestigePointsBonus, prestigePointsMalus);
    }
}
