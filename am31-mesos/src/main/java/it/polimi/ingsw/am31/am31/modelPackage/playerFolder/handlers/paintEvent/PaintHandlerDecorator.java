package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent;

/**
 * Base decorator for {@link IPaintHandler} implementations.
 */
public abstract class PaintHandlerDecorator implements IPaintHandler{

    protected IPaintHandler wrappedHandler;

    /**
     * @param wrappedHandler the {@link IPaintHandler} to wrap
     */
    protected PaintHandlerDecorator(IPaintHandler wrappedHandler){ this.wrappedHandler = wrappedHandler; }

}
