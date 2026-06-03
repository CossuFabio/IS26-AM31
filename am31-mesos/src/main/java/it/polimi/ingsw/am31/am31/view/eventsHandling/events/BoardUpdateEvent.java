package it.polimi.ingsw.am31.am31.view.eventsHandling.events;

import it.polimi.ingsw.am31.am31.view.eventsHandling.ViewEvent;

/**
 * Posted when the board state changes (offer track, turn order or card lines. Even
 * state data like the round status and the tribe or buildings of a player.)
 */
public class BoardUpdateEvent extends ViewEvent {
    public BoardUpdateEvent (){}
}
