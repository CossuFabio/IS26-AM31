package it.polimi.ingsw.am31.am31.modelPackage.boardFolder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

/**
 * Represents an offer card on the offer track, defining the draws and food bonus awarded to the player that places their totem on it.
 * WARNING: may contain null values. Check with the isFree method before trying to access the player.
 */
public class OfferCard{

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

    /**
     * @return The number of draws from the lower line awarded in the ACTION_PHASE to the player that places their totem in this offer card
     */
    public int getDrawFromUnder() {
        return drawFromUnder;
    }

    /**
     * @return The number of draws from the upper line awarded in the ACTION_PHASE to the player that places their totem in this offer card
     */
    public int getDrawFromUpper() {
        return drawFromUpper;
    }

    /**
     * @return the food bonus given to the player that places their totem there
     */
    public int getFood() {
        return food;
    }

    /**
     * @return true if no player placed their totem on this card
     */
    public boolean isFree() {return this.player == null;}

    /**
     * @return the player occupying this offer card
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * @return the minimum number of players required to include this offer card in the game
     */
    public int getMinPlayers() {return minPlayers;}

    /**
     * @param player the player that is trying to put their totem on this offer card
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Removes the player from this offer card
     */
    public void free() {
        this.player = null;
    }

    public String getOfferCardId(){return this.offerCardId;}


}
