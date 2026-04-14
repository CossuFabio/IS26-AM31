package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.exceptions.*;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
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

    //Game attributes
    private int roundNumber;
    private final List<Player> players;
    private final int nPlayers;

    //Game objects
    private Board board;
    private BuildingDeck buildingDeck;
    private TribeDeck tribeDeck;
    private TurnOrder turnOrder;

    //Game state attributes
    private int era;
    //private GameState gameState;
    private RoundPhasesEnum currentRoundPhase;
    private Player playerActing;

    //Game flow helper
    private final TurnDrawManager drawManager;

    //Utilities
    private final GameResources gameResources;

    //Setup
    public Game (int nPlayers, GameResources gameResources) throws IOException {

        roundNumber=0; //set to 1 in gameStart
        players= new ArrayList<Player>();

        era = 1;
        this.nPlayers= nPlayers;
        this.turnOrder = new TurnOrder(nPlayers);
        this.currentRoundPhase = RoundPhasesEnum.GAME_STARTING;

        this.gameResources = gameResources;
        tribeDeck = new TribeDeck(nPlayers, gameResources.getTribeCards());
        buildingDeck = new BuildingDeck(nPlayers, gameResources.getBuildingCards());
        board= new Board(nPlayers, gameResources.getOfferCards());

        this.drawManager = new TurnDrawManager(board);
    }

    public void addPlayer(Player player) throws TooManyPlayersException, UsernameAlreadyTakenException, PlayerColorAlreadyTakenException {
        if(players.stream().anyMatch(inGamePlayer -> inGamePlayer.getNickname().equals(player.getNickname()))) throw new UsernameAlreadyTakenException();
        if(players.stream().map(p -> p.getColor()).anyMatch(c -> c == player.getColor())) throw new PlayerColorAlreadyTakenException(player.getColor());

        if(players.size()<nPlayers){
            players.add(player);
        }
        else throw new TooManyPlayersException();
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    //TODO: Test This
    public void gameStart() throws WrongRoundPhaseException, InsufficientPlayersNumberException, EmptyDeckException {
        
        if(currentRoundPhase != RoundPhasesEnum.GAME_STARTING) throw new WrongRoundPhaseException();

        if(players.size()<nPlayers){
            throw new InsufficientPlayersNumberException();
        }

        //Just a placeholder, otherwise this would be null and risk a NullPointerException. It will be ignored because the first phase
        //Is TOTEM_PLACING
        playerActing = players.getFirst();


        //Assigning random order for the first turn
        Collections.shuffle(players);

        //Giving the correct amount of food
        for (int i = 0; i < players.size(); i++) {
            players.get(i).editFood(GameConstants.STARTING_FOOD[i]);
        }

        //Setting the players in TurnOrder (using the first round function!)
        players.forEach(p -> turnOrder.setPlayerFirstRound(p));

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

        this.roundNumber = 1;
        this.currentRoundPhase = RoundPhasesEnum.TOTEM_PLACING;
    }
    

    //TODO : Test This
    public List<Player> gameEnd() throws WrongRoundPhaseException{

        if(!isGameFinished()) throw new WrongRoundPhaseException();

        currentRoundPhase = RoundPhasesEnum.END_TURN;

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

    public void endRound() throws WrongRoundPhaseException{

        if(!(this.currentRoundPhase == RoundPhasesEnum.BONUS_DRAWING_PHASE && isBonusDrawPhaseFinished())) throw new WrongRoundPhaseException();

        this.currentRoundPhase = RoundPhasesEnum.END_TURN;
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
    public void totemChoiceAction(Player player, OfferCard offerCard) throws WrongPlayerTurnException, EverybodyPlayedException, OfferTrackTileAlreadyTakenException, WrongRoundPhaseException {
        if(currentRoundPhase != RoundPhasesEnum.TOTEM_PLACING) {
            throw new WrongRoundPhaseException();
        }
        if (!(player == getPlayerActingTotemPhase())) throw new WrongPlayerTurnException();
        if (offerCard.isFree())
            offerCard.setPlayer(player);
        else
            throw new OfferTrackTileAlreadyTakenException();
        turnOrder.goToNextPlayer();
    }

    public void playerDrawFromUpper(Player player, IPickable card) throws WrongPlayerTurnException, CardNotFoundException, InvalidPickException, InvalidDrawException, WrongRoundPhaseException{

        //Check if the phase is correct
        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE && currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE) {throw new WrongRoundPhaseException();}

        //Check if the draw comes from the correct player
        if (!player.equals(playerActing)) throw new WrongPlayerTurnException();

        //Check if player can draw from top
        if (!drawManager.canDrawFromUpper()) throw new InvalidDrawException();

        //Throws invalid pick exception
        card.canPick(player);

        //Throws not found exception
        board.drawFromUpper(card);

        drawManager.drawUpper();
        //This method handles the dispatch of which deck will the card be added (TribeDeck or BuildingDeck)

        card.addToPlayer(player);

    }

    public void playerDrawFromLower(Player player, IPickable card) throws WrongPlayerTurnException, CardNotFoundException, InvalidPickException, InvalidDrawException, WrongRoundPhaseException{

        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE && currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE) {throw new WrongRoundPhaseException();}

        //Check if the draw comes from the correct player
        if(!player.equals(playerActing)) throw new WrongPlayerTurnException();

        //Check if player can draw from top
        if(!drawManager.canDrawFromLower()) throw new InvalidDrawException();

        //Throws invalid pick exception
        card.canPick(player);

        //Throws not found exception
        board.drawFromLower(card);


        drawManager.drawLower();

        //This method handles the dispatch of which deck will the card be added (TribeDeck or BuildingDeck)
        card.addToPlayer(player);

    }



    //Flags for controller
    public boolean isGameFinished(){
        //false values are placeholder
        return (roundNumber == GameConstants.ROUNDS_NUMBER && currentRoundPhase == RoundPhasesEnum.END_TURN);
    }
    public boolean isTotemPlacingPhaseFinished() throws WrongRoundPhaseException{
        if(currentRoundPhase != RoundPhasesEnum.TOTEM_PLACING) {throw new WrongRoundPhaseException();}
        return turnOrder.everybodyPlayed();
    }
    public boolean isDrawPhaseFinished() throws WrongRoundPhaseException{

        //Phase must be ACTION_PHASE
        if (this.currentRoundPhase != RoundPhasesEnum.ACTION_PHASE) {
            throw new WrongRoundPhaseException();
        }

        //If no offer card is occupied, everybody has drawed
        return board.getOfferCards().stream()
                .noneMatch(offerCard -> !offerCard.isFree());
    }

    //TODO NOT SURE IF CORRECT - REVIEW
    public boolean isBonusDrawPhaseFinished() throws WrongRoundPhaseException{
        if (this.currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE) {throw new WrongRoundPhaseException();}
        if(players.stream().noneMatch(player -> player.hasBonusDraw())) return true;
        return drawManager.hasFinishedDrawing();
    }



    //---Getters---
    public TurnOrder getTurnOrder(){
        return turnOrder;
    }
    public Board getBoard(){return board;}
    public GameResources getGameResources(){return this.gameResources; }
    public List<Player> getPlayersList(){return players.stream().toList();}

    //May differ from players.size() in case of disconnections!
    public int getNumPlayers(){return this.nPlayers;}


    public Player getPlayerActingDrawPhase(){
        return this.playerActing;
    }
    public Player getPlayerActingTotemPhase(){
        return turnOrder.getPlayerActing();
    }

    public RoundPhasesEnum getCurrentRoundPhase(){return this.currentRoundPhase;}
    public boolean hasCurrentPlayerFinishedDrawing(){return this.drawManager.hasFinishedDrawing();}



    //---Setters---
    //Sets up the next player and how many cards should it draw

    public void setUpPlayerActing(Player nextPlayerActing, OfferCard offerCardChosen){

        this.playerActing = nextPlayerActing;
        drawManager.setUp(offerCardChosen.getDrawFromUpper(), offerCardChosen.getDrawFromUnder());

        //If zero nothing changes, else adds food
        playerActing.editFood(offerCardChosen.getFood());

    }

    public void setUpDrawingPhase() throws WrongRoundPhaseException, IllegalAccessException {

        //Must check if still in TOTEM_PLACING_PHASE
        if(this.currentRoundPhase != RoundPhasesEnum.TOTEM_PLACING) throw new WrongRoundPhaseException();

        //Checks if TOTEM_PLACING_PHASE is finished - IllegalAccessException is still a placeholder
        if(!turnOrder.everybodyPlayed()) throw new IllegalAccessException();

        this.currentRoundPhase  = RoundPhasesEnum.ACTION_PHASE;

        Optional<OfferCard> firstPlayer = board.getOfferCards().stream()
                    .filter(offerCard -> !offerCard.isFree()).findFirst();

        //Shouldn't happen but better checking
        if(!firstPlayer.isPresent()) throw new IllegalStateException();

        OfferCard offerCard = firstPlayer.get();
        setUpPlayerActing(offerCard.getPlayer(), offerCard);

    }

    public void setCurrentRoundPhase(RoundPhasesEnum currentRoundPhase){this.currentRoundPhase = currentRoundPhase;}

    public void setNextPlayerDrawing() throws IllegalStateException, WrongRoundPhaseException{

        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE) throw new WrongRoundPhaseException();
        if(!drawManager.hasFinishedDrawing()) throw new IllegalStateException("Wrong usage of method setNextPlayerDrawing()! Previous player must finish drawing");

        //Must free the Offer Card of the previous player
        Optional<OfferCard> previousPlayerOfferCard = board.getOfferCards().stream().filter(card -> !card.isFree()).findFirst();

        //This check should never fail but it is better to check
        if(!previousPlayerOfferCard.isPresent()) throw new IllegalStateException("Something went wrong in setNextPlayerDrawing()");

        //Frees the offerCard
        turnOrder.setPlayer(previousPlayerOfferCard.get().getPlayer());
        previousPlayerOfferCard.get().free();


        Optional<OfferCard> nextOfferCard = board.getOfferCards().stream().filter(card -> !card.isFree()).findFirst();

        //If no player is found, the ACTION_PHASE has ended (no one is on the offer track)
        if(!nextOfferCard.isPresent()){
            return;
        }

        OfferCard offerCard = nextOfferCard.get();
        Player nextPlayer = offerCard.getPlayer();


        setUpPlayerActing(nextPlayer, offerCard);


    }

    public void setUpBonusDrawingPhase(){

        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE) throw new WrongRoundPhaseException();

        this.currentRoundPhase = RoundPhasesEnum.BONUS_DRAWING_PHASE;

        Player playerWithBonus = players.stream().filter(player -> player.hasBonusDraw()).findFirst().orElse(null);

        if(playerWithBonus == null) drawManager.setUp(0, 0);
        else drawManager.setUp(playerWithBonus.getBonusDrawFromUpper(), playerWithBonus.getBonusDrawFromLower());


    }

    public void setUpTotemPlacingPhase(){
        if(currentRoundPhase != RoundPhasesEnum.END_TURN) throw new WrongRoundPhaseException();
        this.currentRoundPhase = RoundPhasesEnum.TOTEM_PLACING;
        roundNumber++;
    }

}
