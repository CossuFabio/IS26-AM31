package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

/**
 * Posted when joining a lobby fails, carrying a human-readable error message
 */
public class FailedJoinLobby extends ViewEvent {

    private final String message;

    public FailedJoinLobby(String message){
        this.message = message;
    }

    public String getMessage(){return message; }

}
