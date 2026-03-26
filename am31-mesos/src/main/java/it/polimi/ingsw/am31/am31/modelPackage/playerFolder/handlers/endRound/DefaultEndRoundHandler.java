package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound;

import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class DefaultEndRoundHandler implements IEndRoundHandler{
    public DefaultEndRoundHandler(){}

    @Override
    //By default, no action is taken during the end of the round
    public void handleEndRound(Player player) {}
}
