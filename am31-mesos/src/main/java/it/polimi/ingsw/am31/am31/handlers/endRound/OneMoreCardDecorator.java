package it.polimi.ingsw.am31.am31.handlers.endRound;

import it.polimi.ingsw.am31.am31.Player;

public class OneMoreCardDecorator extends EndRoundHandlerDecorator {
        public OneMoreCardDecorator(IEndRoundHandler handler) {
            super(handler);
        }

    @Override
    //During the end of the round, the player can draw one more card from the upper line of the board
    public void handleEndRound(Player player) {
        // TO-DO: implement this
        // player.drawFromUpper();

        wrappedHandler.handleEndRound(player);
    }
}
