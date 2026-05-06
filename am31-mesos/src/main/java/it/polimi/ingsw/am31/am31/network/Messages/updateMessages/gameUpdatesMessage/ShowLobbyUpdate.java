package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

public class ShowLobbyUpdate extends UpdateMessage {

    private final List<LobbyDescriptor> lobbies;


    @JsonCreator
    public ShowLobbyUpdate( @JsonProperty("lobbies") List<LobbyDescriptor> lobbies){
        super(UpdateMethodsConstants.GAME_SHOW_LOBBY_UPDATE_METHOD);
        this.lobbies = lobbies;

    }

    public List<LobbyDescriptor> getLobbies(){return this.lobbies.stream().toList(); }


    @Override
    protected boolean checkSpecificValidity() {
        return lobbies != null && !lobbies.contains(null) && lobbies.stream().allMatch(l -> l.checkValidity());
    }

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }


}
