package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class FlatPrestigePointsDecorator extends EndGameHandlerDecorator {

    private static int PRESTIGE_POINTS_BONUS = 25;

    public FlatPrestigePointsDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        //25 bonus prestigePoints
        player.editPrestigePoints(PRESTIGE_POINTS_BONUS);

        wrappedHandler.handleEndGame(player);
    }
}
