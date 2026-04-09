package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.EverybodyPlayedException;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

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
        int place = currentPlayer;
        players.set(place, player);
        //end-turn effects get solved directly by player handler
        player.resolveEndTurn(place, numPlayers);
        this.currentPlayer++;
        if(currentPlayer == numPlayers) //index gets reset for next round
            currentPlayer = 0;
    }
    //when every player is on the OfferTrack, TurnOrder resets. It is then rebuilt with setPlayer which is called
    //every time a player finishes their move on the OfferTrack to set the new order for next turn.

    //For the first round they must not resolve their end turn effect!
    public void setPlayerFirstRound(Player player){
        int place = currentPlayer;
        players.add(place, player);
        this.currentPlayer++;
        if(currentPlayer == numPlayers) //index gets reset for next round
            currentPlayer = 0;

    }

    public Player getPlayerActing(){
        return players.get(currentPlayer);
    }

    public void reset(){
        players.clear();
        currentPlayer = 0;
    }

    //TODO TESTING
    public void goToNextPlayer() throws EverybodyPlayedException{
        if(currentPlayer == numPlayers) throw new EverybodyPlayedException();
        players.set(currentPlayer, null);
        this.currentPlayer = currentPlayer + 1;
    }


    public boolean everybodyPlayed(){ return currentPlayer == numPlayers; }

}

