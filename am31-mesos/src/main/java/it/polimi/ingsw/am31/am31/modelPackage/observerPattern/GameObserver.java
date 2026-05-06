package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.TurnOrder;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

public interface GameObserver {

    //Notifies observer that they have been removed
    void notifyRemoveMe();

    void onPlayerNewBuildingEvent(Player player);
    void onPlayerScoresUpdate(Player player);
    void onPlayerTribeUpdate(Player player);

    void onGameRoundStatusUpdate(Game game);
    void onPlayersListUpdate(Game game);

    void onCardLineUpdate(Board board, BoardRows row);
    void onOfferTrackUpdate(Board board);
    void onTurnOrderUpdate(TurnOrder turnOrder);
    void onGameStartUpdate(Game game);
    void onGameEndUpdate(List<Player> leaderboard);
    void onGameEventResolveUpdate(EventCard c);

    void onGameCrashUpdate();

    String getIdentifier();
}
