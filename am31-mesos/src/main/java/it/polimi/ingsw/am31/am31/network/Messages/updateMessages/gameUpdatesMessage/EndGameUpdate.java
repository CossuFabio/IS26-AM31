package it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateMethodsConstants;

import java.util.List;

//Notifies the players that the game has ended and sends the leaderboard
public class EndGameUpdate extends UpdateMessage {
    //TODO: Should leaderboard also show scores?
    private final List<PlayerMessage> playersLeaderBoard;

    public EndGameUpdate(@JsonProperty List<PlayerMessage> playersList){
        super(UpdateMethodsConstants.GAME_END_UPDATE);
        this.playersLeaderBoard = playersList;
    }

    public List<PlayerMessage> getPlayersLeaderBoard() {return playersLeaderBoard;}

    @Override
    protected boolean checkSpecificValidity() {return playersLeaderBoard != null && !playersLeaderBoard.contains(null);}

}
