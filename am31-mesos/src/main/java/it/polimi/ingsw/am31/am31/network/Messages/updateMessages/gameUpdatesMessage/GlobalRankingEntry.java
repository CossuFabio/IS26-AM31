package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GlobalRankingEntry {

    private final String playerNickname;
    private final int totalPrestigePoints;
    private final int totalFood;
    private final int gamesPlayed;
    private final int rank;

    @JsonCreator
    public GlobalRankingEntry(
            @JsonProperty("playerNickname") String playerNickname,
            @JsonProperty("totalPrestigePoints") int totalPrestigePoints,
            @JsonProperty("totalFood") int totalFood,
            @JsonProperty("gamesPlayed") int gamesPlayed,
            @JsonProperty("rank") int rank) {
        this.playerNickname = playerNickname;
        this.totalPrestigePoints = totalPrestigePoints;
        this.totalFood = totalFood;
        this.gamesPlayed = gamesPlayed;
        this.rank = rank;
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public int getTotalPrestigePoints() {
        return totalPrestigePoints;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getRank() {
        return rank;
    }

}
