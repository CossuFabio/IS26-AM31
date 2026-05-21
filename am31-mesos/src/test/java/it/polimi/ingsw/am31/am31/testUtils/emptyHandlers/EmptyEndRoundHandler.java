package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound.IEndRoundHandler;

public class EmptyEndRoundHandler implements IEndRoundHandler {

    public EmptyEndRoundHandler() {}

    @Override
    public void handleEndRound(Player player) {}
}
