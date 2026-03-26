package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.paintEvent;

public abstract class PaintHandlerDecorator implements IPaintHandler{

    protected IPaintHandler wrappedHandler;

    protected PaintHandlerDecorator(IPaintHandler wrappedHandler){ this.wrappedHandler = wrappedHandler; }

}
