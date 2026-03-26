package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

public class HunterPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public HunterPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        CountVisitor visitor = new CountVisitor();
        player.getTribe().forEach(card -> card.acceptVisit(visitor));
        player.editPrestigePoints(3*visitor.getHunters());

        wrappedHandler.handleEndGame(player);
    }
}
