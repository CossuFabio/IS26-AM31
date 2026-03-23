package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.Player;

public class FlatPrestigePointsDecorator extends EndGameHandlerDecorator {

    public FlatPrestigePointsDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        //25 bonus prestigePoints
        player.editPrestigePoints(25);

        wrappedHandler.handleEndGame(player);
    }
}
