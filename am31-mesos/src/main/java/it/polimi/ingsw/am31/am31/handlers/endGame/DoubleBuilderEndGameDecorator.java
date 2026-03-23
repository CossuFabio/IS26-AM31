package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.Player;

public class DoubleBuilderEndGameDecorator extends EndGameHandlerDecorator {

    public DoubleBuilderEndGameDecorator(IEndGameHandler handler) {
        super(handler);
    }

    @Override
    public void handleEndGame(Player player) {
        //Gets bonus for builders a second time
        int bonus = player.getTribe().stream().mapToInt(card -> card.getPrestigePoints()).sum();
        player.editPrestigePoints(bonus);
        wrappedHandler.handleEndGame(player);
    }
}