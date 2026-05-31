package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.TurnOrder;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;

import java.util.List;

/**
 * Implements the observer pattern.
 * Class that implements this class are notified about changes on the state of object they are observing.
 */
public interface GameObserver {

    //Notifies observer that they have been removed

    /**
     * Notifies an observer that it has been removed from the observers of an object
     */
    void notifyRemoveMe();

    /**
     * Notifies that the list of buildings of a player changed
     * @param player the player whose buildings changed
     */
    void onPlayerNewBuildingEvent(Player player);

    /**
     * Notifies that the food or the prestige points of a player have changed
     * @param player the player whose score changed
     */
    void onPlayerScoresUpdate(Player player);

    /**
     * Notifies that the player tribe changed
     * @param player the player whose tribe changed
     */
    void onPlayerTribeUpdate(Player player);

    /**
     * Notifies changes on round phase or round number
     * @param game observed game instance
     */
    void onGameRoundStatusUpdate(Game game);

    /**
     * Notifies that a player joined or left the lobby
     * @param game observed game instance
     */
    void onPlayersListUpdate(Game game);

    /**
     * Notifies that a card line changed
     * @param board observed board instance that contains card lines
     * @param row the row that changed
     */
    void onCardLineUpdate(Board board, BoardRows row);

    /**
     * Notifies changes on totems on the offer track
     * @param board the observed board instance
     */
    void onOfferTrackUpdate(Board board);

    /**
     * Notifies changes on the turn order status
     * @param turnOrder the observed turn order instance
     */
    void onTurnOrderUpdate(TurnOrder turnOrder);

    /**
     * Notifies that the game started
     * @param game the observed game instance
     */
    void onGameStartUpdate(Game game);

    /**
     * Notifies that the game ended
     * @param game the observed game instance
     * @param globalRanking List representing the leaderboard of all players based on the number of players in that game
     */
    void onGameEndUpdate(Game game, List<GlobalRankingEntry> globalRanking);

    /**
     * Notifies the resolution of an event card
     * @param c The event that has resolved
     */
    void onGameEventResolveUpdate(EventCard c);

    /**
     * Notifies that the game crash caused by a player leaving the lobby after the game started
     */
    void onGameCrashUpdate();

    /**
     * @return An identifier for the observer
     */
    String getIdentifier();

    /**
     * Notifies that a player acquired a bonus draw for the BONUS_DRAW_PHASE
     * @param player the player who acquired the bonus
     */
    void onPlayerBonusDrawUpdate(Player player);
}
