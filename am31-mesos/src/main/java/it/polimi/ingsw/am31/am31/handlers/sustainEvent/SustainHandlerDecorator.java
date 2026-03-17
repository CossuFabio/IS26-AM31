package it.polimi.ingsw.am31.am31.handlers.sustainEvent;

public abstract class SustainHandlerDecorator implements ISustainHandler{
    protected ISustainHandler wrappedHandler;

    protected SustainHandlerDecorator(ISustainHandler wrappedHandler) {this.wrappedHandler = wrappedHandler;}
}
