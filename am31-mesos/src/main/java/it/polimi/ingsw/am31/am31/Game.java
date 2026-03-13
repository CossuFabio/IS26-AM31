package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;

import java.util.List;

public class Game {
    private int roundNumber;
    private List<Player> players;
    private Board board;
    private Deck<BuildingCard> buildingDeck;
    private Deck<Card> tribeDecks;
    private TurnOrder turnOrder;
    private int nplayers;
    private int era;
    //private GameController gameController;

    public void addPlayer(Player player){
    }

    public void removePlayer(Player player){
    }

    public void gameStart(){}

    public void gameEnd(){}

    public void resetGame(){}

    public void startRound(){}

    public void endRound(){}

    public void changeEra(){}

    public void playerChoice(Player player){
    }

    public void playerAction(){}

    public void handleFood(){}

    public void handlePrestigePoints(){}

    public TurnOrder getTurnOrder(){
        return turnOrder;
    }
}
