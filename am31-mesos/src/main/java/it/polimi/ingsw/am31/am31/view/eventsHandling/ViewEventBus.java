package it.polimi.ingsw.am31.am31.view.eventsHandling;

import com.google.common.eventbus.EventBus;

//Name given to differentiate from the Guava EventBus. I wanted to encapsulate the Guava EventBus so I could
//limit the handling to ViewEvent types
public class ViewEventBus implements IEventBus {

    private final EventBus internalBus;

    public ViewEventBus(){
        this.internalBus = new EventBus();
    }

    @Override
    public void register(Object obj){
        internalBus.register(obj);
    }

    //Don't forget to call this method when unmounting the scene!
    @Override
    public void unregister(Object obj) {
        internalBus.unregister(obj);
    }

    @Override
    public void post(ViewEvent event) {
        internalBus.post(event);
    }



}
