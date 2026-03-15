package it.polimi.ingsw.am31.am31.handlers.endTurn;

public abstract class EndTurnHandlerDecorator implements IEndTurnHandler{

    protected IEndTurnHandler wrappedHandler;
    protected EndTurnHandlerDecorator(IEndTurnHandler wrappedHandler){this.wrappedHandler = wrappedHandler; }

}
