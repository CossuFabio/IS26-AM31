package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;
import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.view.LocalGameState;

public interface GameObserver {
    void onPlayerNewBuildingEvent(Player player);
    void onPlayerScoresUpdate(Player player);
    void onPlayerTribeUpdate(Player player);

    void onGameRoundStatusUpdate(Game game);
    void onPlayersListUpdate(Game game);

    void onCardLineUpdate(Board board, BoardRows row);
    void onOfferTrackUpdate(Board board);
    void onTurnOrderUpdate(Board board);
    void onGameStartUpdate();
}
