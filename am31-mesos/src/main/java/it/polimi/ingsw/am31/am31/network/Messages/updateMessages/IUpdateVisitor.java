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
    void HandleUpdateMessage(CardLineUpdate msg);
    void HandleUpdateMessage(OfferTrackUpdate msg);
    void HandleUpdateMessage(TurnOrderUpdate msg);

    //Game
    void HandleUpdateMessage(GameRoundStatusUpdate msg);
    void HandleUpdateMessage(GameStartUpdate msg);
    void HandleUpdateMessage(PlayersListUpdate msg);
    void HandleUpdateMessage(ShowLobbyUpdate msg);

    void HandleUpdateMessage(EndGameUpdate msg);

    //Player
    void HandleUpdateMessage(PlayerBuildingsUpdate msg);
    void HandleUpdateMessage(PlayerScoresUpdate msg);
    void HandleUpdateMessage(PlayerTribeUpdate msg);
    void HandleUpdateMessage(GameCrashUpdate msg);

    void HandleUpdateMessage(SuccessRegistrationUpdate msg);
}


