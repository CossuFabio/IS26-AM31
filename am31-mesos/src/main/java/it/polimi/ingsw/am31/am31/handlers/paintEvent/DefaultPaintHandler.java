package it.polimi.ingsw.am31.am31.handlers.paintEvent;

import it.polimi.ingsw.am31.am31.Player;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

public class DefaultPaintHandler implements IPaintHandler{

    public DefaultPaintHandler(){}
    //TODO TESTING
    @Override
    public void handlePaint(Player player, int minimumArtistsNumber, int prestigePointsBonus, int prestigePointsMalus) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().
                forEach(card -> card.acceptVisit(visitor));
        if(visitor.getArtists() >= minimumArtistsNumber ){
            player.editPrestigePoints(prestigePointsBonus);
        }
        else{player.editPrestigePoints(-prestigePointsMalus);}
    }
}
