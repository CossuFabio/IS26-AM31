package it.polimi.ingsw.am31.am31.network.messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerBonusDrawUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.serverMessages.SuccessRegistrationUpdate;

/**
 * Visitor interface for all {@link UpdateMessage} subtypes.
 * Each method handles a specific update type; implemented by the client-side state updater
 */
public interface IUpdateVisitor {

    //Board
    void handleUpdateMessage(CardLineUpdate msg);
    void handleUpdateMessage(OfferTrackUpdate msg);
    void handleUpdateMessage(TurnOrderUpdate msg);

    //Game
    void handleUpdateMessage(GameRoundStatusUpdate msg);
    void handleUpdateMessage(GameStartUpdate msg);
    void handleUpdateMessage(PlayersListUpdate msg);
    void handleUpdateMessage(ShowLobbyUpdate msg);
    void handleUpdateMessage(GameEventResolveUpdate msg);
    void handleUpdateMessage(EndGameUpdate msg);

    //Player
    void handleUpdateMessage(PlayerBuildingsUpdate msg);
    void handleUpdateMessage(PlayerScoresUpdate msg);
    void handleUpdateMessage(PlayerTribeUpdate msg);
    void handleUpdateMessage(GameCrashUpdate msg);

    void handleUpdateMessage(SuccessRegistrationUpdate msg);

    void handleUpdateMessage(PlayerBonusDrawUpdate msg);

}


