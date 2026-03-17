package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.Player;

public class ShamanPrestigeBonusDecorator extends EndGameHandlerDecorator {

    public ShamanPrestigeBonusDecorator(IEndGameHandler handler) {super(handler); }

    @Override
    public void handleEndGame(Player player) {
        //TO-DO: implement this
        //player.getPrestigePoints(int bonus);

        wrappedHandler.handleEndGame(player);
    }
}