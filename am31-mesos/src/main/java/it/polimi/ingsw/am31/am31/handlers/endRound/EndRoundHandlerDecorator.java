package it.polimi.ingsw.am31.am31.handlers.endRound;

public abstract class EndRoundHandlerDecorator implements IEndRoundHandler{
    protected IEndRoundHandler wrappedHandler;

    public EndRoundHandlerDecorator(IEndRoundHandler handler){
        this.wrappedHandler = handler;
    }

}
