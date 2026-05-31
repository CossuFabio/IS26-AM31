package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endTurn;

/**
 * Base decorator for {@link IEndTurnHandler} implementations.
 */

public abstract class EndTurnHandlerDecorator implements IEndTurnHandler{

    protected IEndTurnHandler wrappedHandler;

    /**
     * @param wrappedHandler the {@link IEndTurnHandler} to wrap
     */
    protected EndTurnHandlerDecorator(IEndTurnHandler wrappedHandler){this.wrappedHandler = wrappedHandler; }

}
