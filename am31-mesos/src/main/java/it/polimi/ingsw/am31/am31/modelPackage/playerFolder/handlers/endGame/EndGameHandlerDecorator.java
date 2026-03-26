package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

public abstract class EndGameHandlerDecorator implements IEndGameHandler{
    protected IEndGameHandler wrappedHandler;

    protected EndGameHandlerDecorator(IEndGameHandler handler){ this.wrappedHandler =  handler; }
}
