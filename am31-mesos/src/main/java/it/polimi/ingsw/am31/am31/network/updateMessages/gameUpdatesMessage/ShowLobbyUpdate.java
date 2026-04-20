package it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateVisitor;

import java.util.List;

public class ShowLobbyUpdate extends UpdateMessage {

    private final List<LobbyDescriptor> lobbies;

    @JsonCreator
    public ShowLobbyUpdate( @JsonProperty("lobbies") List<LobbyDescriptor> lobbies){
        super(UpdateMethodsConstants.GAME_SHOW_LOBBY_UPDATE_METHOD);
        this.lobbies = lobbies;
    }

    public List<LobbyDescriptor> getLobbies(){return this.lobbies.stream().toList(); }

    public void acceptVisit(UpdateVisitor updateVisitor) {
        updateVisitor.visit(this);
    }
    public void ShowUpdate(){
        for(LobbyDescriptor l : lobbies){
            System.out.println("Partita: " + l.getId() + ", richiede: " + l.getnPlayers() + " giocatori. Giocatori in lobby: " + l.getFreeSlots());
        }
    }
}
