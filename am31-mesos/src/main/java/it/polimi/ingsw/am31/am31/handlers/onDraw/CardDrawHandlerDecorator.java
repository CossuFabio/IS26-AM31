package it.polimi.ingsw.am31.am31.handlers.onDraw;

public abstract class CardDrawHandlerDecorator implements IDrawHandler{

    protected IDrawHandler wrappedHandler;
    protected CardDrawHandlerDecorator(IDrawHandler wrappedHandler){ this.wrappedHandler = wrappedHandler; }

}
