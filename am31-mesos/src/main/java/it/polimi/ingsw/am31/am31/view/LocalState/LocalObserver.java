package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;

import java.util.List;

public interface LocalObserver {
    void onGameStartUpdate();

    void onRoundPhaseUpdate();

    void onRoundNumberUpdate();

    void onShowLobbyUpdate(List<LobbyDescriptor> lobbies);
    //what if there was only one update method?
    void onCardLineUpdate();

    void onPlayerListUpdate();

    void onEraUpdate();

    void onOfferTrackUpdate();

    void onPlayerScoreUpdate();

    void onPlayerTribeUpdate();

    void onTurnOrderUpdate();

    void onLobbyError(String errorMsg);

    void onSuccessRegistration(SuccessRegistrationUpdate msg);
}

