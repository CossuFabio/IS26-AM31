package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class ShamanPrestigeBonusDecorator extends EndGameHandlerDecorator {

    private static int PRESTIGE_POINTS_BONUS = 4;

    public ShamanPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(PRESTIGE_POINTS_BONUS*visitor.getShamans());
        wrappedHandler.handleEndGame(player);
    }
}