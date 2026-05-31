package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endGame;

/**
 * Base decorator for {@link IEndGameHandler} implementations.
 */
public abstract class EndGameHandlerDecorator implements IEndGameHandler{
    protected IEndGameHandler wrappedHandler;

    /**
     * @param handler the {@link IEndGameHandler} to wrap
     */
    protected EndGameHandlerDecorator(IEndGameHandler handler){ this.wrappedHandler =  handler; }
}
