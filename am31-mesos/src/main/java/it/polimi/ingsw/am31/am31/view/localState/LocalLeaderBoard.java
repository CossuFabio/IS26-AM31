package it.polimi.ingsw.am31.am31.view.localState;

/**
 * Represents an entry in the local leaderboard, containing a player's state and whether they are the winner.
 * Immutable Record class
 * @param playerState The local state of the player.
 * @param isWinner True if the player is the winner, false otherwise.
 */
public record LocalLeaderBoard(LocalPlayerState playerState, boolean isWinner) {
}
