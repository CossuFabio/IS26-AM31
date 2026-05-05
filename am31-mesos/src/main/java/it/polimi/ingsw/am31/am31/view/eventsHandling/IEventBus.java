package it.polimi.ingsw.am31.am31.view.eventsHandling;

public interface IEventBus {

    boolean register(Object obj);
    boolean unregister(Object obj);
    void post(ViewEvent event);
}
