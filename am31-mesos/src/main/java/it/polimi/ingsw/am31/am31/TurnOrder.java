package it.polimi.ingsw.am31.am31;

import java.util.ArrayList;
import java.util.List;

public class TurnOrder {
    private ArrayList<Player> players;
    private int currentPlayer;

    public void setPlayer(Player player) {
    }

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public Player getPlayerActing(){
        int i = getCurrentPlayer();
        return players.get(i);
    }

    public void reset(){}
}
