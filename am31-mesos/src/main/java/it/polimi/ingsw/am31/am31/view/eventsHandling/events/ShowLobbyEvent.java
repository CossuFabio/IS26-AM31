package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

import java.util.List;

public class ShowLobbyEvent extends ViewEvent {
    private final List<LobbyDescriptor> lobbies;

    public ShowLobbyEvent(List<LobbyDescriptor> lobbies){
        this.lobbies = lobbies;
    }

    public List<LobbyDescriptor> getLobbies(){return lobbies;}

}
