package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class FarmerPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public FarmerPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {

        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(4*visitor.getFarmers());


        wrappedHandler.handleEndGame(player);
    }
}

