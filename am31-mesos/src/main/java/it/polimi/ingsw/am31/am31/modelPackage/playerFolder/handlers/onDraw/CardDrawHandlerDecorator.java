package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.onDraw;

/**
 * Base decorator for {@link IDrawHandler} implementations.
 */
public abstract class CardDrawHandlerDecorator implements IDrawHandler{

    protected IDrawHandler wrappedHandler;

    /**
     * @param wrappedHandler the {@link IDrawHandler} to wrap
     */
    protected CardDrawHandlerDecorator(IDrawHandler wrappedHandler){ this.wrappedHandler = wrappedHandler; }

}
