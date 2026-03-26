package it.polimi.ingsw.am31.am31;

import java.util.ArrayList;

public class TurnOrder {

    private ArrayList<Player> players;
    private int currentPlayer;
    private final int numPlayers;

    public TurnOrder(int numPlayers){
        this.currentPlayer = 0;
        this.players = new ArrayList<Player>();
        this.numPlayers = numPlayers;
    }

    public void setPlayer(Player player) {
        int place = getCurrentPlayer();
        players.add(place, player);
        //end-turn effects get solved directly by player handler
        player.resolveEndTurn(place, numPlayers);
        this.currentPlayer++;
        if(currentPlayer == numPlayers) //index gets reset for next round
            currentPlayer = 0;
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
    public void goToNextPlayer()
    {
        this.currentPlayer = getCurrentPlayer() + 1;
    }
}