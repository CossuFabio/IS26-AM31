package it.polimi.ingsw.am31.am31.modelPackage.playerFolder.handlers.endRound;

public abstract class EndRoundHandlerDecorator implements IEndRoundHandler{
    protected IEndRoundHandler wrappedHandler;

    protected EndRoundHandlerDecorator(IEndRoundHandler handler){
        this.wrappedHandler = handler;
    }

}
