package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

/**
 * Posted when username registration succeeds, carrying the registered username
 */
public class SuccessRegistrationEvent extends ViewEvent {

    private final String identifier;

    public SuccessRegistrationEvent(String identifier){
        this.identifier = identifier;
    }

    public String getIdentifier(){return identifier; }


}
