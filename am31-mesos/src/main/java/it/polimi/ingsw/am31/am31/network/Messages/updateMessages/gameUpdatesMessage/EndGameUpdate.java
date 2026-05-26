package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.IUpdateVisitor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

//Notifies the players that the game has ended and sends the leaderboard
public class EndGameUpdate extends UpdateMessage {

    private final List<LeaderBoardEntryUpdate> playersLeaderBoard;
    private final List<GlobalRankingEntry> globalRanking;

    @JsonCreator
    public EndGameUpdate(
            @JsonProperty("playersLeaderBoard") List<LeaderBoardEntryUpdate> playersList,
            @JsonProperty("globalRanking") List<GlobalRankingEntry> globalRanking){
        super(UpdateMethodsConstants.GAME_END_UPDATE);
        this.playersLeaderBoard = playersList;
        this.globalRanking = globalRanking;
    }

    public List<LeaderBoardEntryUpdate> getPlayersLeaderBoard() {return playersLeaderBoard;}
    public List<GlobalRankingEntry> getGlobalRanking() {return globalRanking;}

    @Override
    protected boolean checkSpecificValidity() {return playersLeaderBoard != null && !playersLeaderBoard.contains(null);}

    @Override
    public void acceptVisit(IUpdateVisitor visitor){
        visitor.handleUpdateMessage(this);
    }
}
