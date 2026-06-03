package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

/**
 * Posted when an in-game action fails, carrying a human-readable error message
 */
public class InGameErrorEvent extends ViewEvent {
    private String errorMessage;
    public InGameErrorEvent(String errorMessage) {
        this.errorMessage=errorMessage;
    }
    public String getErrorMessage(){return errorMessage;}
}
