package it.polimi.ingsw.am31.am31.testUtils.emptyHandlers;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame.IEndGameHandler;

public class EmptyEndGameHandler implements IEndGameHandler {

    public EmptyEndGameHandler() {}

    @Override
    public void handleEndGame(Player player) {}
}
