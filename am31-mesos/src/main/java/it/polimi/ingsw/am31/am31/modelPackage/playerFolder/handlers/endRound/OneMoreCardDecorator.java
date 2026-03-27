package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class OneMoreCardDecorator extends EndRoundHandlerDecorator {
        public OneMoreCardDecorator(IEndRoundHandler handler) {
            super(handler);
        }

    @Override
    //During the end of the round, the player can draw one more card from the upper line of the board
    public void handleEndRound(Player player) {
        //TODO: implement this, and test
        // playerDrawFromUpper(player, card);

        wrappedHandler.handleEndRound(player);
    }
}
