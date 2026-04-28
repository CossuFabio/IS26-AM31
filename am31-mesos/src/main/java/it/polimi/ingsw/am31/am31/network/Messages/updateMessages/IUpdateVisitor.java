package it.polimi.ingsw.am31.am31.network.Messages.updateMessages;

import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GameRoundStatusUpdate;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

public interface IUpdateVisitor {
//    void visit (CardLineUpdate cardLineUpdate);
//    void visit (OfferCardMessage offerCardMessage);
//    void visit (OfferTrackUpdate offerTrackUpdate);
    boolean visit(ShowLobbyUpdate showLobbyUpdate);
    boolean visit (GameRoundStatusUpdate gameRoundStatusUpdate);
}
