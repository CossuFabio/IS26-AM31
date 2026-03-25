package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.cards.BuildingCard;
import it.polimi.ingsw.am31.am31.cards.Card;
import it.polimi.ingsw.am31.am31.cards.EventCard;
import it.polimi.ingsw.am31.am31.cards.IPickable;
import it.polimi.ingsw.am31.am31.visitor.CountVisitor;

import java.io.IOException;
import java.util.*;

import static java.util.Comparator.*;

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
    public Game (int nPlayers) throws IOException {
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

    //TODO: Implement This
    public void gameStart(){

    }

    //TODO : Test This
    public void gameEnd(){
        players.forEach(player->{player.resolveEndGame();});
        List<Player> scores = new  ArrayList<>();
        for (Player player : players) {
            scores.add(player);
        }
        scores.sort(comparingInt(Player::getPrestigePoints));
        ArrayList<Player> winners = new ArrayList<>();
        winners.add(scores.removeLast());
        while(!scores.isEmpty()){
            Player playerToCompare = scores.removeLast();
            if(winners.getFirst().getPrestigePoints() == playerToCompare.getPrestigePoints()){
                if(winners.getFirst().getFood() < playerToCompare.getFood()) {
                    winners.removeFirst();
                    winners.add(playerToCompare);
                }
                else if(winners.getFirst().getFood()==playerToCompare.getFood())
                    winners.add(playerToCompare);
            }
        }
        //method should then show winners
    }

    //TODO: Test this
    public void resetGame() throws IOException {
        players.forEach(player->{player.editFood(-player.getFood());});
        players.forEach(player->{player.editPrestigePoints(-player.getPrestigePoints());});
        board = new Board(nPlayers);
        buildingDeck = new BuildingDeck(nPlayers);
        tribeDeck = new TribeDeck(nPlayers);
        gameStart();
    }

    //TODO: Implement This
    public void startRound(){

    }


    private void resolveEvents(){

        PriorityQueue<EventCard> eventQueue = new PriorityQueue<>(
                comparingInt(EventCard::getPriority)
        );
        CountVisitor visitor = new CountVisitor();
        ArrayList<Card> templine = board.getUnderLine();

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

        //Cannot use foreach (See documentation)
        while(!eventQueue.isEmpty()){
            eventQueue.poll().resolve(players);
        }
    }

    public void endRound(){

        //Players handle the end of the round
        players.forEach(player -> player.resolveEndRound());
        resolveEvents();
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

    public void playerChoice(Player player, OfferCard offerCard){
        offerCard.setPlayer(player);
    }


    public void playerDrawFromUpper(Player player, IPickable card){
        board.drawFromUpper(card);
        card.addToPlayer(player);
    }

    public void playerDrawFromTop(Player player, IPickable card){
        board.drawFromUpper(card);
        card.addToPlayer(player);
    }



    public TurnOrder getTurnOrder(){
        return turnOrder;
    }


}
