package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.huntEvent;

public abstract class HuntHandlerDecorator implements IHuntHandler{

    protected IHuntHandler wrappedHandler;

    protected HuntHandlerDecorator(IHuntHandler wrappedHandler){this.wrappedHandler = wrappedHandler; }

}
