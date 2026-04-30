package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;


import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.am31.am31.network.Messages.IMessageVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.Message;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

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
        @JsonSubTypes.Type(value = GameCrashUpdate.class,       name = UpdateMethodsConstants.GAME_CRASHED_METHOD),
        @JsonSubTypes.Type(value = SuccessRegistrationUpdate.class,       name = UpdateMethodsConstants.USERNAME_ACCEPTED_METHOD),
        @JsonSubTypes.Type(value = GameStartUpdate.class,       name = UpdateMethodsConstants.GAME_START_UPDATE),
})


public abstract class UpdateMessage extends Message {

    public static final String messageType = "UPDATE";

    @JsonIgnore
    private final String updateType;


    protected UpdateMessage(String updateType){
        super(messageType);
        this.updateType = updateType;
    }

    @JsonIgnore
    public String getUpdateType(){return updateType;}


    @JsonIgnore
    public final boolean checkValidity(){
        return updateType != null && checkSpecificValidity();
    }

    protected abstract boolean checkSpecificValidity();

    @Override
    public void acceptVisit(IMessageVisitor visitor){
        visitor.visitUpdate(this);
    }

    public abstract void acceptVisit(IUpdateVisitor visitor);


}

