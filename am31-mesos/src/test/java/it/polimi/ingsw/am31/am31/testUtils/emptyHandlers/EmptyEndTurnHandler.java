package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn.IEndTurnHandler;

public class EmptyEndTurnHandler implements IEndTurnHandler {

    public EmptyEndTurnHandler() {}

    @Override
    public void handleEndTurn(Player player, int order, int numPlayers) {}
}
