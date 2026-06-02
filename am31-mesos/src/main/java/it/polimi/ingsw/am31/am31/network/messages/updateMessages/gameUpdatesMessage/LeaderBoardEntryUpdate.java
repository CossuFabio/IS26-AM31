package it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO representing a single entry in the end-of-game leaderboard; used inside {@link EndGameUpdate}
 */
public class LeaderBoardEntryUpdate {

    private final String playerNickname;
    private final int prestigePoints;
    private final int food;
    private final boolean isWinner;

    @JsonCreator
    public LeaderBoardEntryUpdate(
            @JsonProperty("playerNickname") String playerNickname,
            @JsonProperty("prestigePoints") int prestigePoints,
            @JsonProperty("food")int food,
            @JsonProperty("isWinner") boolean isWinner) {
        this.playerNickname = playerNickname;
        this.prestigePoints = prestigePoints;
        this.food = food;
        this.isWinner = isWinner;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public int getPrestigePoints() {
        return prestigePoints;
    }

    public int getFood() {
        return food;
    }

    @JsonProperty("isWinner")
    public boolean isWinner() {
        return isWinner;
    }
}
