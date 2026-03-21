package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;
import it.polimi.ingsw.am31.am31.cards.EventCard;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

import java.util.*;

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

    public void gameEnd(){
        players.forEach(player->{player.resolveEndGame();});
    }

    public void resetGame(){}

    public void startRound(){}

    public void endRound(){

        players.forEach(player -> player.resolveEndRound());

        CountVisitor visitor = new CountVisitor();
        ArrayList<Card> templine = board.getUnderLine();
        PriorityQueue<EventCard> eventQueue = new PriorityQueue<EventCard>();
        int tempevent=0;
        while(!templine.isEmpty()) {
            templine.getFirst().acceptVisit(visitor);
            if (visitor.getEvent() > tempevent)
            {
                eventQueue.add((EventCard) templine.getFirst());  //Safe explicit cast to EventCard
                tempevent=visitor.getEvent();
            }

        templine.removeFirst();
        }
        //TODO FIX THIS, and TEST
        Collections.sort(eventQueue, new Comparator<EventCard>() {
            public int compare(EventCard o1, EventCard o2) {
                return o1.getPriority() - o2.getPriority()
            }
        });

        eventQueue.forEach(eventCard -> eventCard.resolve(players));
        board.moveLowerTribes();
        for(int i=0;i<players.size()+4;i++)
            board.addUpper(drawTCard());


    }




    private Card drawTCard () {
        Card temp = tribeDeck.draw();
        if (temp.getEra()>this.era)
            changeEra();
        return temp;
    }
    private Card drawBCard () {
        Card temp = buildingDeck.draw();
        if (temp.getEra()>this.era)
            changeEra();
        return temp;
    }

    public void changeEra(){
        board.moveLowerBuildings();
        ArrayList<BuildingCard> temp = board.getUnderBLine();
        //we increase the era, then check if the next card in building deck is the new era -> add it to upperbline.
        era++;
        while(temp.getFirst().getEra()==era) {
            board.addBuildingUpper((BuildingCard) buildingDeck.draw()); //explicit Cast to buildingcard
            temp.removeFirst();
        }
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
