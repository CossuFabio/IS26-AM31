package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent;

/**
 * Base decorator for {@link IHuntHandler} implementations.
 */
public abstract class HuntHandlerDecorator implements IHuntHandler{

    protected IHuntHandler wrappedHandler;

    /**
     * @param wrappedHandler the {@link IHuntHandler} to wrap
     */
    protected HuntHandlerDecorator(IHuntHandler wrappedHandler){this.wrappedHandler = wrappedHandler; }

}
