package it.polimi.ingsw.am31.am31;

import java.util.ArrayList;
import java.util.List;

public class TurnOrder {
    private ArrayList<Player> players;
    private int currentPlayer;

    public TurnOrder(){
        this.currentPlayer = 0;
        this.players = new ArrayList<Player>();
    }

    public void setPlayer(Player player, int playerNumber) {
        int place = getCurrentPlayer();
        if (place < playerNumber) {
            players.add(place, player);
            this.currentPlayer = place++;
        }
        if (place == playerNumber)
            this.currentPlayer = 0;
    }
    //when every player is on the OfferTrack, TurnOrder resets. It is then rebuilt with setPlayer which is called
    //every time a player finishes their move on the OfferTrack to set the new order for next turn.


    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public Player getPlayerActing(){
        return players.get(getCurrentPlayer());
    }

    public void reset(){
        players.clear();
        currentPlayer = 0;
    }
//TODO TESTING
    public void goToNextPlayer(){
        this.currentPlayer = getCurrentPlayer() + 1;
    }
}