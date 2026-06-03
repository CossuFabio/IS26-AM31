package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

import java.util.List;

/**
 * Posted when the list of players in the lobby changes before the game starts.
 * Carries the updated player list
 */
public class PlayersInLobbyChangedEvent extends ViewEvent {

    private final List<LocalPlayerState> players;

    public PlayersInLobbyChangedEvent(List<LocalPlayerState> players){
        this.players = players;
    }

    public List<LocalPlayerState> getPlayers(){return players; }

}
