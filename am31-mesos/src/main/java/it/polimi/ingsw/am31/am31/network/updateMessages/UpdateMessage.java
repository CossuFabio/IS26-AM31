package it.polimi.ingsw.am31.am31.network.updateMessages;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.PlayersListUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "updateType")

@JsonSubTypes({
        @JsonSubTypes.Type(value = ShowLobbyUpdate.class,       name = UpdateMethodsConstants.GAME_SHOW_LOBBY_UPDATE_METHOD),
        @JsonSubTypes.Type(value = PlayersListUpdate.class,     name = UpdateMethodsConstants.GAME_PLAYERS_LIST_UPDATE_METHOD),
        @JsonSubTypes.Type(value = GameRoundStatusUpdate.class, name = UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD),
        @JsonSubTypes.Type(value = PlayerScoresUpdate.class,    name = UpdateMethodsConstants.PLAYER_SCORES_UPDATE_METHOD),
        @JsonSubTypes.Type(value = PlayerTribeUpdate.class,     name = UpdateMethodsConstants.PLAYER_TRIBES_UPDATE_METHOD),
        @JsonSubTypes.Type(value = PlayerBuildingsUpdate.class, name = UpdateMethodsConstants.PLAYER_BUILDINGS_UPDATE_METHOD),
        @JsonSubTypes.Type(value = OfferTrackUpdate.class,      name = UpdateMethodsConstants.BOARD_OFFERCARD_UPDATE_METHOD),
        @JsonSubTypes.Type(value = CardLineUpdate.class,        name = UpdateMethodsConstants.BOARD_CARDLINE_UPDATE_METHOD),
        @JsonSubTypes.Type(value = TurnOrderUpdate.class,       name = UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD),
})
public abstract class UpdateMessage {


    private final String updateType;


    protected UpdateMessage(String updateType){
        this.updateType = updateType;
    }

    @JsonIgnore
    public String getUpdateType(){return updateType; }

    public void acceptVisit (UpdateVisitor updateVisitor) { return; }
    public void ShowUpdate () {System.out.println("SUcA");}
}
