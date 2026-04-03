package it.polimi.ingsw.am31.am31.modelPackage.boardFolder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.exceptions.OfferTrackTileAlreadyTakenException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

public class OfferCard {

    private final int food;
    private final int drawFromUpper;
    private final int drawFromUnder;
    private final int minPlayers;
    private final String offerCardId;
    private Player player;

    //private int drawFromUpperThisTurn = 0
    //private int drawFromLowerThisTurn = 0

    @JsonCreator
    public OfferCard(
            @JsonProperty("offerCardId") String offerCardId,
            @JsonProperty("food") int food,
            @JsonProperty("drawFromUnder")int drawFromUnder,
            @JsonProperty("drawFromUpper") int drawFromUpper,
            @JsonProperty("minPlayers")int minPlayers){
        this.offerCardId = offerCardId;
        this.food = food;
        this.drawFromUnder = drawFromUnder;
        this.drawFromUpper = drawFromUpper;
        this.minPlayers= minPlayers;
        this.player = null;
    }

    public int getDrawFromUnder() {
        return drawFromUnder;
    }

    public int getDrawFromUpper() {
        return drawFromUpper;
    }

    public int getFood() {
        return food;
    }

    public boolean isFree() {return this.player == null;}

    public Player getPlayer(){
        return player;
    }

    public int getMinPlayers() {return minPlayers;}

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void free() {
        this.player = null;
    }

    public String getOfferCardId(){return this.offerCardId;}

}
