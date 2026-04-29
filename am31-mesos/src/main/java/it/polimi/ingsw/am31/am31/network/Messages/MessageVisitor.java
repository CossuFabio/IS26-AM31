package it.polimi.ingsw.am31.am31.network.Messages;

import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.view.LocalState.StateUpdater;

public class MessageVisitor implements IMessageVisitor {

    public MessageVisitor(){}

    @Override
    public void visitError(ErrorMessage message) {
        System.out.println("[ERROR] " + message.getMessage());
    }

    @Override
    public void visitUpdate(UpdateMessage message, StateUpdater updater) {
        if(message == null || !message.checkValidity()) return;
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_SHOW_LOBBY_UPDATE_METHOD)){
            ShowLobbyUpdate lobbyUpdate = (ShowLobbyUpdate) message;
            updater.HandleUpdateMessage(lobbyUpdate);
        }
        if (message.getUpdateType().equals(UpdateMethodsConstants.GAME_ROUND_UPDATE_METHOD)) {
            GameRoundStatusUpdate gameRoundUpdate = (GameRoundStatusUpdate) message;
            updater.HandleUpdateMessage(gameRoundUpdate);
        }
        if (message.getUpdateType().equals(UpdateMethodsConstants.GAME_START_UPDATE)){
            GameStartUpdate gameStartUpdate = (GameStartUpdate) message;
            updater.HandleUpdateMessage(gameStartUpdate);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.BOARD_CARDLINE_UPDATE_METHOD)) {
            CardLineUpdate update = (CardLineUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.BOARD_OFFERCARD_UPDATE_METHOD)) {
            OfferTrackUpdate update = (OfferTrackUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.BOARD_TURNORDER_UPDATE_METHOD)) {
            TurnOrderUpdate update = (TurnOrderUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_PLAYERS_LIST_UPDATE_METHOD)) {
            PlayersListUpdate update = (PlayersListUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.PLAYER_SCORES_UPDATE_METHOD)) {
            PlayerScoresUpdate update = (PlayerScoresUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_CRASHED_METHOD)) {
            GameCrashUpdate update = (GameCrashUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.PLAYER_BUILDINGS_UPDATE_METHOD)) {
            PlayerBuildingsUpdate update = (PlayerBuildingsUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.PLAYER_TRIBES_UPDATE_METHOD)) {
            PlayerTribeUpdate update = (PlayerTribeUpdate) message;
            updater.HandleUpdateMessage(update);
        }
        if(message.getUpdateType().equals(UpdateMethodsConstants.GAME_END_UPDATE)) {
            EndGameUpdate update = (EndGameUpdate) message;
            updater.HandleUpdateMessage(update);
        }
    }
}