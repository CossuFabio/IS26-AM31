package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

public class InGameErrorEvent extends ViewEvent {
    private String errorMessage;
    public InGameErrorEvent(String errorMessage) {
        this.errorMessage=errorMessage;
    }
    public String getErrorMessage(){return errorMessage;}
}
