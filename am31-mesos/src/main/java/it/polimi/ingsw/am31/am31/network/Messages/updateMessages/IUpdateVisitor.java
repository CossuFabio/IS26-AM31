package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.boardUpdates.TurnOrderUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.*;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerBuildingsUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerScoresUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.playerUpdatesMessage.PlayerTribeUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;

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

    void handleUpdateMessage(EndGameUpdate msg);

    //Player
    void handleUpdateMessage(PlayerBuildingsUpdate msg);
    void handleUpdateMessage(PlayerScoresUpdate msg);
    void handleUpdateMessage(PlayerTribeUpdate msg);
    void handleUpdateMessage(GameCrashUpdate msg);

    void handleUpdateMessage(SuccessRegistrationUpdate msg);
}


