package it.polimi.ingsw.am31.am31.view.eventsHandling;

public interface IEventBus {

    void register(Object obj);
    void unregister(Object obj);
    void post(ViewEvent event);
}
