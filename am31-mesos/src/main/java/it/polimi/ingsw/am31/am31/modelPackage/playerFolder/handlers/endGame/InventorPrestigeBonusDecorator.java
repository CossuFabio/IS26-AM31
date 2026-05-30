package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

/** Awards bonus prestige points per inventor in the tribe at end of game. */
public class InventorPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public static final int PRESTIGE_POINTS_BONUS = 2;

    public InventorPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(PRESTIGE_POINTS_BONUS*visitor.getInventors());

        wrappedHandler.handleEndGame(player);
    }
}
