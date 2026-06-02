package it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;

import java.util.List;

/**
 * DTO describing an available lobby: game ID, total player slots, free slots, and available colors.
 * Used inside {@link ShowLobbyUpdate}
 */
public class LobbyDescriptor {

    private final Integer id;
    private final Integer nPlayers;
    private final Integer freeSlots;
    private final List<Color> availableColors;
    @JsonCreator
    public LobbyDescriptor(@JsonProperty("id") Integer id,
                           @JsonProperty("nPlayers") Integer nPlayers,
                           @JsonProperty("freeSlots") Integer freeSlots,
                           @JsonProperty("availableColors") List<Color> availableColors) {
        this.id = id;
        this.nPlayers = nPlayers;
        this.freeSlots = freeSlots;
        this.availableColors = availableColors;
    }
    public Integer getId() {return id;}
    public Integer getnPlayers(){return nPlayers; }
    public Integer getFreeSlots(){return freeSlots; }
    public List<Color> getAvailableColors(){return this.availableColors.stream().toList();}


    public boolean checkValidity(){
        return id != null && nPlayers != null && freeSlots != null;
    }

}
