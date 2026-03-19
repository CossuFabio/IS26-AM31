package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int roundNumber;
    private List<Player> players;
    private Board board;
    private BuildingDeck buildingDeck;
    private TribeDeck tribeDeck;
    private TurnOrder turnOrder;
    private int era;
    //private GameState gameState;
    int nPlayers;

    //Setup
    public Game (int nPlayers) {
        roundNumber=0; //o 1
        players= new ArrayList<Player>();
        board= new Board(nPlayers);
        buildingDeck = new BuildingDeck(nPlayers);
        tribeDeck = new TribeDeck(nPlayers);
        era = 1;
        this.nPlayers= nPlayers;
    }

    public void addPlayer(Player player){
        if(players.size()<nPlayers){
            players.add(player);
        }
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public void gameStart(){}

    public void gameEnd(){}

    public void resetGame(){}

    public void startRound(){}

    public void endRound(){

    }

    public void changeEra(){

    }

    public void playerChoice(Player player){
    }

    public void playerAction(){}

    public void handleFood(){}

    public void handlePrestigePoints(){}

    public TurnOrder getTurnOrder(){
        return turnOrder;
    }



}
