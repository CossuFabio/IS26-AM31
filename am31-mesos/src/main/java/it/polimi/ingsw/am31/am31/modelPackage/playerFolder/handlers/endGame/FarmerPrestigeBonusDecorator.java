package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class FarmerPrestigeBonusDecorator extends EndGameHandlerDecorator {

    private static int PRESTIGE_POINTS_BONUS = 4;

    public FarmerPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {

        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(PRESTIGE_POINTS_BONUS*visitor.getFarmers());


        wrappedHandler.handleEndGame(player);
    }
}

