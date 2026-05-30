package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

/** Awards bonus prestige points per farmer in the tribe at end of game. */
public class FarmerPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public static final int PRESTIGE_POINTS_BONUS = 4;

    public FarmerPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {

        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(PRESTIGE_POINTS_BONUS*visitor.getFarmers());


        wrappedHandler.handleEndGame(player);
    }
}

