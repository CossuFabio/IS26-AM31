package it.polimi.ingsw.am31.am31.handlers.endGame;

import it.polimi.ingsw.am31.am31.Player;

public class CardSetDecorator extends EndGameHandlerDecorator {
    public CardSetDecorator(IEndGameHandler handler) { super(handler);}

    @Override
    public void handleEndGame(Player player) {
        //TO-DO: implement this
        //player.editPrestigePoints(int bonus);

        wrappedHandler.handleEndGame(player);
    }
}
