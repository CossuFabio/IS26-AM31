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
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;

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
    private final int nPlayers;
    private RoundPhasesEnum currentRoundPhase;
    private final TurnDrawManager drawManager;
    private Player playerActing;

    private final GameResources gameResources;

    //Setup
    public Game (int nPlayers, GameResources gameResources) throws IOException {

        roundNumber=0; //o 1
        players= new ArrayList<Player>();

        era = 1;
        this.nPlayers= nPlayers;
        this.turnOrder = new TurnOrder(nPlayers);
        this.currentRoundPhase = RoundPhasesEnum.TOTEM_PLACING;
        this.drawManager = new TurnDrawManager();

        this.gameResources = gameResources;
        tribeDeck = new TribeDeck(nPlayers, gameResources.getTribeCards());
        buildingDeck = new BuildingDeck(nPlayers, gameResources.getBuildingCards());
        board= new Board(nPlayers, gameResources.getOfferCards());
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
        for (int i = 0; i < players.size(); i++) {
            players.get(i).editFood(GameConstants.STARTING_FOOD[i]);
        }

        //Setting the players in TurnOrder
        players.forEach(p -> turnOrder.setPlayer(p));

        //Setup the underLine
        CountVisitor eventCounter = new CountVisitor();
        int numberEvents = eventCounter.getEvent();
        Card card;
        for(int i = 0; i<(nPlayers + GameConstants.LOWER_LINE_EXTRA_CARDS + numberEvents); i++){

            card = tribeDeck.draw();
            card.acceptVisit(eventCounter);

            //Event => addUpper, not event => addLower
            if(eventCounter.getEvent() > numberEvents) board.addUpper(card);
            else board.addLower(card);

            numberEvents = eventCounter.getEvent();
        }

        //Fills the upper trail of cards
        for(int i = 0; i<(nPlayers + GameConstants.UPPER_LINE_EXTRA_CARDS - numberEvents); i++){
            board.addUpper(tribeDeck.draw());
        }

        //Set up buildings trail (only upper)
        for(int i = 0; i <GameConstants.getEraOneBuildings(nPlayers); i++)
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
        board = new Board(nPlayers, gameResources.getOfferCards());
        buildingDeck = new BuildingDeck(nPlayers, gameResources.getBuildingCards());
        tribeDeck = new TribeDeck(nPlayers, gameResources.getTribeCards());
        turnOrder = new TurnOrder(nPlayers);
        era = 1;
        try{
            gameStart();
        }
        catch(InsufficientPlayersNumberException e){
            System.err.println(e.getMessage());
        }
    }


//    public void DrawChoiceAction(Player, Card)
    //turn draw manager, gestisce le carte da pescare e se può pescare.
    //se al player spetta pescata, legge la carta e pesca. drawfrom* controlla se la carta c'è e tt cose.
    //dopo la action,aggiorna le cardremaining, controlla se il player sta apposto
    //(controller) in tal caso, passa alla tessera dopo, HandleEndTurn e setup con il giocatore nuovo. -> se finito
    //(controller) cambio di fase a END_TURN
    //FINE





    //TODO: fix this (Discuss together)
    private void resolveEvents(){

        //TODO Fix usage of priority queue
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
            for(int i=0;i<players.size()+ GameConstants.UPPER_LINE_EXTRA_CARDS ;i++) {
                board.addUpper(drawTCard());
            }
        }
        catch (EmptyDeckException e) { System.err.println(e.getMessage()); }

        //setPhase TOTEM_PLACING

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

    //prende la scelta, controlla se fattibile, la fa, rimette il player in ordine.
    //se non fattibile, lancia eccezione o del player o tessera già presa
    //se finito, turnorder lancia exception, catchata da controller
    //(controller)in tal caso fa setup della TurnDrawManager e assegna cibo della tessera (caso tessera n1).
    public void totemChoiceAction(Player player, OfferCard offerCard) throws WrongPlayerTurnException, EverybodyPlayedException, OfferTrackTileAlreadyTakenException {
        if(!(player == getPlayerActingTotemPhase())) throw new WrongPlayerTurnException();
        if(offerCard.isFree())
            offerCard.setPlayer(player);
        else
            throw new OfferTrackTileAlreadyTakenException();
        turnOrder.goToNextPlayer();
    }


    public void playerDrawFromUpper(Player player, IPickable card) throws WrongPlayerTurnException, CardNotFoundException, InvalidPickException, InvalidDrawException{

        //Check if the draw comes from the correct player
        if(!player.equals(playerActing)) throw new WrongPlayerTurnException();

        //Check if player can draw from top
        if(!drawManager.canDrawFromUpper()) throw new InvalidDrawException();

        //Throws invalid pick exception
        card.canPick(player);

        //Throws not found exception
        board.drawFromUpper(card);

        //This method handles the dispatch of which deck will the card be added (TribeDeck or BuildingDeck)
        drawManager.drawUpper();
        card.addToPlayer(player);

    }

    public void playerDrawFromLower(Player player, IPickable card) throws WrongPlayerTurnException, CardNotFoundException, InvalidPickException, InvalidDrawException{

        //Check if the draw comes from the correct player
        if(!player.equals(playerActing)) throw new WrongPlayerTurnException();

        //Check if player can draw from top
        if(!drawManager.canDrawFromLower()) throw new InvalidDrawException();

        //Throws invalid pick exception
        card.canPick(player);

        //Throws not found exception
        board.drawFromLower(card);

        //This method handles the dispatch of which deck will the card be added (TribeDeck or BuildingDeck)
        drawManager.drawLower();
        card.addToPlayer(player);

    }

    //game.getOfferTrack()
    //foreach track
    //se non vuota => prende giocatore e fa setupplayeracting(player, offertrack)
    //quando deve aggiornare => va al prossimo
    //Quando finiscoono => fase = fineturno


    public TurnOrder getTurnOrder(){
        return turnOrder;
    }

    public Board getBoard(){return board;}

    //Sets up the next player and how many cards should it draw
    public void setUpPlayerActing(Player nextPlayerActing, OfferCard offerCardChosen){
        this.playerActing = nextPlayerActing;
        drawManager.setUp(offerCardChosen.getDrawFromUpper(), offerCardChosen.getDrawFromUnder());

        //If zero nothing changes, else adds food
        playerActing.editFood(offerCardChosen.getFood());

    }

    public Player getPlayerActingDrawPhase(){
        return this.playerActing;
    }

    public Player getPlayerActingTotemPhase(){
        return turnOrder.getPlayerActing();
    }

    public RoundPhasesEnum getCurrentRoundPhase(){return this.currentRoundPhase;}

    public void setCurrentRoundPhase(RoundPhasesEnum currentRoundPhase){this.currentRoundPhase = currentRoundPhase;}

    public boolean isGameFinished(){
        //false values are placeholder
        return (
                roundNumber == GameConstants.ROUNDS_NUMBER || false //Check PHASE
                );
    }

    public GameResources getGameResources(){return this.gameResources; }
    public List<Player> getPlayersList(){return players.stream().toList();}
    public int getNumPlayers(){return this.nPlayers;}

}
