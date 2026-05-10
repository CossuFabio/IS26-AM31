package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class DefaultPaintHandler implements IPaintHandler{

    public DefaultPaintHandler(){}

    @Override
    public void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().
                forEach(card -> card.acceptVisit(visitor));
        if(visitor.getArtists() >= minimumArtistsNumber ){
            player.editPrestigePoints(prestigePointsBonus*visitor.getArtists());
        }
        else{player.editPrestigePoints(-prestigePointsMalus);}
    }
}
