package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LobbyDescriptor {

    private final int id;
    private final int nPlayers;
    private final int freeSlots;

    @JsonCreator
    public LobbyDescriptor(@JsonProperty("id") int id,
                           @JsonProperty("nPlayers") int nPlayers,
                           @JsonProperty("freeSlots") int freeSlots) {
        this.id = id;
        this.nPlayers = nPlayers;
        this.freeSlots = freeSlots;
    }
    public int getId() {return id;}
    public int getnPlayers(){return nPlayers; }
    public int getFreeSlots(){return freeSlots; }
}
