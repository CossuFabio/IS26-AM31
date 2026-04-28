package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LobbyDescriptor {

    private final Integer id;
    private final Integer nPlayers;
    private final Integer freeSlots;

    @JsonCreator
    public LobbyDescriptor(@JsonProperty("id") Integer id,
                           @JsonProperty("nPlayers") Integer nPlayers,
                           @JsonProperty("freeSlots") Integer freeSlots) {
        this.id = id;
        this.nPlayers = nPlayers;
        this.freeSlots = freeSlots;
    }
    public Integer getId() {return id;}
    public Integer getnPlayers(){return nPlayers; }
    public Integer getFreeSlots(){return freeSlots; }



    public boolean checkValidity(){
        return id != null && nPlayers != null && freeSlots != null;
    }

}
