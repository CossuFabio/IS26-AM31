package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

/** Awards bonus prestige points per artist in the tribe at end of game. */
public class ArtistPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public final static int PRESTIGE_POINTS_BONUS = 4;

    public ArtistPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(PRESTIGE_POINTS_BONUS*visitor.getArtists());

        wrappedHandler.handleEndGame(player);
    }
}

