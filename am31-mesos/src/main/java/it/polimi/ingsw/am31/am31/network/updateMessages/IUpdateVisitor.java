package it.polimi.ingsw.am31.am31.network.updateMessages;

import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.CardLineUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferCardMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferTrackUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

public interface IUpdateVisitor {
//    void visit (CardLineUpdate cardLineUpdate);
//    void visit (OfferCardMessage offerCardMessage);
//    void visit (OfferTrackUpdate offerTrackUpdate);
    boolean visit(ShowLobbyUpdate showLobbyUpdate);
    boolean visit (GameRoundStatusUpdate gameRoundStatusUpdate);
}
