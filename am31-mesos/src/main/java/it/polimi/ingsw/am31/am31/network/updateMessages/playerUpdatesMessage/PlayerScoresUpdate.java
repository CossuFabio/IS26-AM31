package it.polimi.ingsw.am31.am31.network.updateMessages.playerUpdatesMessage;

import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMethodsConstants;

public class PlayerScoresUpdate extends UpdateMessage {

    private final String playerId;
    private final int newPrestigePoints;
    private final int newFood;

    public PlayerScoresUpdate(String playerId, int newPrestigePoints, int newFood){
        super(UpdateMethodsConstants.PLAYER_SCORES_UPDATE_METHOD);
        this.playerId = playerId;
        this.newPrestigePoints = newPrestigePoints;
        this.newFood = newFood;
    }

    public String getPlayerId(){return this.playerId; }
    public int getNewPrestigePoints(){return this.newPrestigePoints;}
    public int getNewFood(){return this.newFood;}


}
