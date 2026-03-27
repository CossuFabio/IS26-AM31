package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.*;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.BuildingDeck;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.TribeDeck;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;

import java.io.IOException;
import java.util.*;

import static java.util.Comparator.*;

public class Game {
    private int roundNumber;
    private final List<Player> players;
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
        this.turnOrder = new TurnOrder(nPlayers);
    }

    public void addPlayer(Player player) throws TooManyPlayersException {
        if(players.size()<nPlayers){
            players.add(player);
        }
        else throw new TooManyPlayersException();
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    //TODO: Test This
    public void gameStart() throws InsufficientPlayersNumberException, EmptyDeckException {

        if(players.size()<nPlayers){
            throw new InsufficientPlayersNumberException();
        }

        //Assigning random order for the first turn
        Collections.shuffle(players);

        //Giving the correct amount of food
        int[] startingFood = {2, 3, 3, 4, 4};
        for (int i = 0; i < players.size(); i++) {
            players.get(i).editFood(startingFood[i]);
        }

        //Setting the players in TurnOrder
        players.forEach(p -> turnOrder.setPlayer(p));

        //Setup the underLine
        CountVisitor eventCounter = new CountVisitor();
        int numberEvents = eventCounter.getEvent();
        Card card;
        for(int i = 0; i<(nPlayers + 1 + numberEvents); i++){

            card = tribeDeck.draw();
            card.acceptVisit(eventCounter);

            //Event => addUpper, not event => addLower
            if(eventCounter.getEvent() > numberEvents) board.addUpper(card);
            else board.addLower(card);

            numberEvents = eventCounter.getEvent();
        }

        //Fills the upper trail of cards
        for(int i = 0; i<(nPlayers + 4 - numberEvents); i++){
            board.addUpper(tribeDeck.draw());
        }

        //Set up buildings trail (only upper)
        board.addUpper((BuildingCard)buildingDeck.draw());
        if(nPlayers > 2)
            board.addUpper((BuildingCard)buildingDeck.draw());

    }
    

    //TODO : Test This
    public List<Player> gameEnd(){
        players.forEach(player->{player.resolveEndGame();});
        List<Player> scores = new  ArrayList<>();
        for (Player player : players) {
            scores.add(player);
        }
        scores.sort(Comparator.comparing(Player::getPrestigePoints).thenComparing(Player::getFood));
        ArrayList<Player> winners = new ArrayList<>();
        winners.add(scores.removeLast());
        while(!scores.isEmpty()){
            Player playerToCompare = scores.removeLast();
            if(winners.getFirst().getPrestigePoints() == playerToCompare.getPrestigePoints()){
                if(winners.getFirst().getFood() == playerToCompare.getFood()) {
                    winners.add(playerToCompare);
                }
                else return winners;
            }
        }
        return winners;
        //method should then show winners
    }

    //TODO: Test this
    public void resetGame() throws IOException, EmptyDeckException{
        players.forEach(player->{player.editFood(-player.getFood());});
        players.forEach(player->{player.editPrestigePoints(-player.getPrestigePoints());});
        board = new Board(nPlayers);
        buildingDeck = new BuildingDeck(nPlayers);
        tribeDeck = new TribeDeck(nPlayers);
        turnOrder = new TurnOrder(nPlayers);
        era = 1;
        try{
            gameStart();
        }
        catch(InsufficientPlayersNumberException e){
            System.err.println(e.getMessage());
        }
    }

    //TODO: Put this in Controller
    public void startRound(){
        roundNumber++;
//        every player decides where to put their totems on the OfferTrack
//        for(int i = 0; i < nPlayers; i++){
//          Player playerActing = turnOrder.getPlayerActing();
//          Controller reads action from player and gets OfferCard wanted
//            if(board.OfferCard.isFree()){
//            OfferCard.setPlayer(player);
//            TurnOrder.goToNextPlayer();
//          }else
//            catch(OfferTrackTileAlreadyTaken e){
//              System.err.println(e.getMessage());
//          }
//        }
//        turnOrder.reset();
//        int CurrentOfferCard = 0;
//        while(CurrentOfferCard < board.getOfferTrackSize()){
//          OfferCard offerCardInUse = board.getOfferCards().get(CurrentOfferCard);
//          Player playerActing = offerCardInUse.getPlayer();
//          int foodToGive = offerCardInUse.getFood();
//          if(foodToGive != 0){
//            playerActing.editFood(foodToGive);
//            turnOrder.setPlayer(playerActing);
//            offerCardInUse.free();
//          }
//          else{
//             int numberOfDrawsFromUpper = offerCardInUse.getDrawFromUpper();
//             int numberOfDrawsFromUnder = offerCardInUse.getDrawFromUnder();
//             int i;
//             for( i = 0; i < numberOfDrawsFromUnder; i++){
//                 gets player's choice from Controller
//                  playerDrawFromLower(playerActing, cardToDraw);
//             }
//             for( i = 0; i < numberOfDrawsFromUpper; i++){
//                gets player's choice from Controller
//                playerDrawFromUpper(playerActing, cardToDraw);
//             }
//             turnOrder.setPlayer(playerActing);
//             offerCardInUse.free();
//          }
//          CurrentOfferCard++;
//        }
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
        try{
            for(int i=0;i<players.size()+4;i++) {
                board.addUpper(drawTCard());
            }
        }
        catch (EmptyDeckException e) { System.err.println(e.getMessage()); }
    }

    private Card drawTCard () throws EmptyDeckException {
        Card temp = tribeDeck.draw();
        if (temp.getEra()>this.era)
            changeEra();
        return temp;
    }
    private Card drawBCard () throws EmptyDeckException{
        Card temp = buildingDeck.draw();
        if (temp.getEra()>this.era)
            changeEra();
        return temp;
    }

    //TODO: TEST THIS
    public void changeEra(){
        board.moveLowerBuildings();
        //we increase the era, then check if the next card in building deck is the new era -> add it to upperbline.
        this.era++;
        try{
            while(buildingDeck.getNextCardEra()==era) {
                board.addUpper( buildingDeck.draw());
            }
        }
        catch(EmptyDeckException e){
            System.err.println();
        }
    }

    //TODO: THINK
    public void playerChoice(Player player, OfferCard offerCard){
        offerCard.setPlayer(player);
    }

    //TODO: BAD - MUST REDO
    public void playerDrawFromUpper(Player player, CharacterCard card) throws CardNotFoundException{
        board.drawFromUpper(card);
        player.addCard(card);
    }

    public void playerDrawFromLower(Player player, CharacterCard card) throws CardNotFoundException{
        board.drawFromLower(card);
        player.addCard(card);
    }

    public void playerDrawFromUpper(Player player, BuildingCard card) throws CardNotFoundException, InsufficientFoodException{

        if(player.getFood() + player.getBuildersDiscount() < card.getCost()) throw new InsufficientFoodException(player.getFood(), player.getBuildersDiscount(), card.getCost());
        board.drawFromUpper(card);

        int finalFoodCost =  card.getCost() - player.getBuildersDiscount();
        //finalFoodCost<0 means that player's discount is greater than food cost, so player shouldn't pay
        //any food
        player.editFood(-Math.max(0, finalFoodCost));

        player.addCard(card);

    }

    public void playerDrawFromLower(Player player, BuildingCard card) throws CardNotFoundException, InsufficientFoodException{

        if(player.getFood() + player.getBuildersDiscount() < card.getCost()) throw new InsufficientFoodException(player.getFood(), player.getBuildersDiscount(), card.getCost());
        board.drawFromLower(card);

        int finalFoodCost =  card.getCost() - player.getBuildersDiscount();
        //finalFoodCost<0 means that player's discount is greater than food cost, so player shouldn't pay
        //any food
        player.editFood(-Math.max(0, finalFoodCost));

        player.addCard(card);

    }



    public TurnOrder getTurnOrder(){
        return turnOrder;
    }

    public Board getBoard(){return board;}

}
