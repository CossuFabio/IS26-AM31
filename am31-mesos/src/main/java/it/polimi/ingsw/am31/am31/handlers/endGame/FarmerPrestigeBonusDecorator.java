package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.Player;

public class FarmerPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public FarmerPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        //TO-DO: implement this
        //player.getPrestigePoints(int bonus);

        wrappedHandler.handleEndGame(player);
    }
}

