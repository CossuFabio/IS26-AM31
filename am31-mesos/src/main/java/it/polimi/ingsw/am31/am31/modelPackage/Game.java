package it.polimi.ingsw.am31.am31.modelPackage;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.*;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.*;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EmptyDeckException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.IncorrectMethodCallException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.InsufficientPlayersNumberException;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.EventQueueBuilderVisitor;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.BuildingDeck;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.TribeDeck;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.resources.GameResources;

import java.io.IOException;
import java.util.*;



/**
 * Model class representing a running game of Mesos.
 * Holds all game state (board, players, decks, current phase) and exposes
 * state-mutating operations that enforce phase pre-conditions, throwing checked
 * exceptions on violation.
 *
 * <p>Observers registered via {@link #setObserverHandler} are notified of every
 * state change.</p>
 */
public class Game implements GameObservable {

    //Game attributes
    private int roundNumber;
    private final List<Player> players;
    private final int nPlayers;

    //Game objects
    private final Board board;
    private final BuildingDeck buildingDeck;
    private final TribeDeck tribeDeck;
    private final TurnOrder turnOrder;

    //Game state attributes
    private int era;
    //private GameState gameState;
    private RoundPhasesEnum currentRoundPhase;
    private Player playerActing;

    //Game flow helper
    private final TurnDrawManager drawManager;

    //Utilities
    private final GameResources gameResources;
    private ObserverHandler observers;

    //Setup
    /**
     * Creates a new game for nPlayers players.
     * Game resources (decks, offer cards) are injected via {@link GameResources},
     * allowing tests to supply controlled card sets without touching the file system.
     *
     * @param nPlayers number of players (between MIN_PLAYERS and MAX_PLAYERS)
     * @param gameResources the card and offer-tile data to use for this game
     * @throws InvalidPlayersNumberException if nPlayers is out of range
     * @throws IOException if game resources cannot be loaded
     */
    public Game (int nPlayers, GameResources gameResources) throws IOException, InvalidPlayersNumberException {

        roundNumber=0; //set to 1 in gameStart
        players= new ArrayList<Player>();

        era = 1;
        if(nPlayers  < GameConstants.MIN_PLAYERS || nPlayers > GameConstants.MAX_PLAYERS)
            throw new InvalidPlayersNumberException();
        this.nPlayers= nPlayers;
        this.turnOrder = new TurnOrder(nPlayers);

        this.currentRoundPhase = RoundPhasesEnum.GAME_STARTING;


        this.gameResources = gameResources;
        tribeDeck = new TribeDeck(nPlayers, gameResources.getTribeCards());
        buildingDeck = new BuildingDeck(nPlayers, gameResources.getBuildingCards());
        board= new Board(nPlayers, gameResources.getOfferCards());
        this.drawManager = new TurnDrawManager(board);
        this.observers = new GameObserversSet();

    }

    //Must be called when creating game
    @Override
    public void setObserverHandler(ObserverHandler gameObserver) {
        this.observers = gameObserver;
        board.setObserverHandler(gameObserver);
        turnOrder.setObserverHandler(this.observers);
        players.forEach(p -> p.setObserverHandler(observers));
    }

    /**
     * Adds a player to the lobby. Only valid before the game has started.
     *
     * @param player the player to add
     * @throws GameAlreadyStartedException if the game has already started
     * @throws TooManyPlayersException if the lobby is already full
     * @throws UsernameAlreadyTakenException if the nickname is already in use
     * @throws PlayerColorAlreadyTakenException if the chosen color is already taken
     */
    public void addPlayer(Player player) throws GameAlreadyStartedException, TooManyPlayersException, UsernameAlreadyTakenException, PlayerColorAlreadyTakenException {
        if(currentRoundPhase != RoundPhasesEnum.GAME_STARTING) throw new GameAlreadyStartedException();
        if(players.stream().anyMatch(inGamePlayer -> inGamePlayer.getNickname().equals(player.getNickname()))) throw new UsernameAlreadyTakenException();
        if(players.stream().map(p -> p.getColor()).anyMatch(c -> c == player.getColor())) throw new PlayerColorAlreadyTakenException(player.getColor());

        if(players.size()<nPlayers){
            players.add(player);
            player.setObserverHandler(this.observers);
            observers.onPlayersListUpdate(this);
        }
        else throw new TooManyPlayersException();
    }
    public void removePlayer(Player player){
        if(players.remove(player))
            observers.onPlayersListUpdate(this);
    }


    /**
     * Starts the game: randomises the first-round turn order, distributes starting food,
     * fills the board lines, and transitions to {@link RoundPhasesEnum#TOTEM_PLACING}.
     * Requires all expected players to be in the lobby.
     *
     * @throws IncorrectMethodCallException if the game has already started
     * @throws InsufficientPlayersNumberException if not all players have joined yet
     * @throws EmptyDeckException if the tribe deck is unexpectedly empty during setup
     */
    public void gameStart() throws IncorrectMethodCallException, InsufficientPlayersNumberException, EmptyDeckException {
        
        if(currentRoundPhase != RoundPhasesEnum.GAME_STARTING) throw new IncorrectMethodCallException("gameStart", "Game already started");

        if(players.size()<nPlayers){
            throw new InsufficientPlayersNumberException();
        }
        observers.onGameStartUpdate(this);
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

        roundNumber = 1;
        currentRoundPhase = RoundPhasesEnum.TOTEM_PLACING;
        observers.onGameRoundStatusUpdate(this);
    }

    public void gameEnd() throws IncorrectMethodCallException{

        if(!isGameFinished()) throw new IncorrectMethodCallException("gameEnd", "Game not finished!");

        currentRoundPhase = RoundPhasesEnum.END_TURN;
        observers.onGameRoundStatusUpdate(this);

        players.forEach(player->{player.resolveEndGame();});

    }

    //You can get the leaderboard at any moment
    public List<Player> getLeaderBoard(){

        return players.stream()
                .sorted(Comparator
                        .comparingInt(Player::getPrestigePoints)
                        .thenComparingInt(Player::getFood)
                        .reversed())
                .toList();
    }

    public boolean isPlayerWinner(Player p){
        if(p == null || !players.contains(p)) return false;

        //Sorting the leaderboard is O(1) since the size is limited to 5
        Player top = getLeaderBoard().getFirst();

        return p.getPrestigePoints() == top.getPrestigePoints()
                && p.getFood() == top.getFood();
    }

//why private?
    //private void resolveEvents(){
    /**
     * Resolves all event cards in the lower line. On the final round, also resolves
     * event cards in the upper line. The sustain event is always resolved last.
     */
        public void resolveEvents(){

        EventQueueBuilderVisitor eventVisitor = new EventQueueBuilderVisitor();
        board.getUnderLine().forEach(card -> card.acceptVisit(eventVisitor));

        if(roundNumber == GameConstants.ROUNDS_NUMBER)
            board.getUpperLine().forEach(card -> card.acceptVisit(eventVisitor));

        List<EventCard> eventCards = eventVisitor.getCompleteQueue();

        eventCards.forEach(eventCard -> {
            eventCard.resolve(players);
            observers.onGameEventResolveUpdate(eventCard);
        });


    }

    public void endRound() throws IncorrectMethodCallException{

        if(!(this.currentRoundPhase == RoundPhasesEnum.BONUS_DRAWING_PHASE && isBonusDrawPhaseFinished())) throw new IncorrectMethodCallException("endRound");

        this.currentRoundPhase = RoundPhasesEnum.END_TURN;
        observers.onGameRoundStatusUpdate(this);
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


    public void changeEra(){
        board.moveLowerBuildings();
        //we increase the era, then check if the next card in building deck is the new era -> add it to upperbline.
        this.era++;
        try{
            while(buildingDeck.getNextCardEra()==era) {
                //explicit cast, to be sure they are put in the right line
                board.addUpper((BuildingCard) buildingDeck.draw());
            }
        }
        catch(EmptyDeckException e){
            System.err.println("Empty deck!");
        }
    }



    //prende la scelta, controlla se fattibile, la fa, rimette il player in ordine.
    //se non fattibile, lancia eccezione o del player o tessera già presa
    //se finito, turnorder lancia exception, catchata da controller
    //(controller)in tal caso fa setup della TurnDrawManager e assegna cibo della tessera (caso tessera n1).

    /**
     * Places the player's totem on the chosen offer tile during the totem-placing phase.
     *
     * @param player the player placing the totem
     * @param offerCard the offer tile chosen by the player
     * @throws WrongRoundPhaseException if not in the totem-placing phase
     * @throws WrongPlayerTurnException if it is not this player's turn
     * @throws OfferTrackTileAlreadyTakenException if the chosen tile is already occupied
     * @throws IncorrectMethodCallException if the method is called in an invalid state
     */
    public void totemChoiceAction(Player player, OfferCard offerCard) throws WrongPlayerTurnException, IncorrectMethodCallException, OfferTrackTileAlreadyTakenException, WrongRoundPhaseException {
        if(currentRoundPhase != RoundPhasesEnum.TOTEM_PLACING) {
            throw new WrongRoundPhaseException();
        }
        if (!(player == getPlayerActingTotemPhase())) throw new WrongPlayerTurnException();
        if (offerCard.isFree()) {
            offerCard.setPlayer(player);
            observers.onOfferTrackUpdate(board);
        }
        else
            throw new OfferTrackTileAlreadyTakenException();
        turnOrder.goToNextPlayer();
    }

    /**
     * Draws a card from the upper line for the acting player.
     *
     * @param player the player drawing the card
     * @param card the card to draw
     * @throws WrongRoundPhaseException if not in action or bonus-draw phase
     * @throws WrongPlayerTurnException if it is not this player's turn
     * @throws InvalidDrawException if the player has no upper draws remaining
     * @throws InvalidPickException if the player cannot afford the card
     * @throws CardNotFoundException if the card is not in the upper line
     */
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

    /**
     * Draws a card from the lower line for the acting player.
     *
     * @param player the player drawing the card
     * @param card the card to draw
     * @throws WrongRoundPhaseException if not in action or bonus-draw phase
     * @throws WrongPlayerTurnException if it is not this player's turn
     * @throws InvalidDrawException if the player has no lower draws remaining
     * @throws InvalidPickException if the player cannot afford the card
     * @throws CardNotFoundException if the card is not in the lower line
     */
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
        return (roundNumber == GameConstants.ROUNDS_NUMBER && currentRoundPhase == RoundPhasesEnum.END_TURN);
    }
    public boolean isTotemPlacingPhaseFinished() throws IncorrectMethodCallException{
        if(currentRoundPhase != RoundPhasesEnum.TOTEM_PLACING) {throw new IncorrectMethodCallException("isTotemPlacingPhaseFinished", "Wrong phase");}
        return turnOrder.everybodyPlayed();
    }
    public boolean isDrawPhaseFinished() throws IncorrectMethodCallException{

        //Phase must be ACTION_PHASE
        if (this.currentRoundPhase != RoundPhasesEnum.ACTION_PHASE) {
            throw new IncorrectMethodCallException("isDrawPhaseFinished", "Wrong phase");
        }

        //If no offer card is occupied, everybody has drawed
        return board.getOfferCards().stream()
                .noneMatch(offerCard -> !offerCard.isFree());
    }
    public boolean isGameInStartingPhase(){return currentRoundPhase == RoundPhasesEnum.GAME_STARTING;}


    /**
     * Returns whether the bonus-draw phase is over.
     * Returns true immediately if no player owns the bonus-draw building.
     *
     * @throws IncorrectMethodCallException if not in the bonus-draw phase
     */
    public boolean isBonusDrawPhaseFinished() throws IncorrectMethodCallException{
        if (this.currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE) {throw new IncorrectMethodCallException("isBonusDrawFinished", "WrongPhase");}
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
    public int getRoundNumber(){return this.roundNumber;}
    public int getEra(){ return this.era;}
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

    /**
     * Sets the acting player and configures the draw manager according to the chosen
     * offer tile. Also grants any food bonus indicated on the tile.
     *
     * @param nextPlayerActing the player who will act next
     * @param offerCardChosen the offer tile chosen by that player
     * @throws IncorrectMethodCallException if not in action or bonus-draw phase
     */
    public void setUpPlayerActing(Player nextPlayerActing, OfferCard offerCardChosen) throws IncorrectMethodCallException{
        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE && currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE) throw new IncorrectMethodCallException("setUpPlayerActing", "Wrong phase");
        this.playerActing = nextPlayerActing;
        drawManager.setUp(offerCardChosen.getDrawFromUpper(), offerCardChosen.getDrawFromUnder());

        //If zero nothing changes, else adds food
        playerActing.editFood(offerCardChosen.getFood());

    }

    protected void setRound(int round)
        {
        this.roundNumber = round;
        }

    public void setUpDrawingPhase() throws IncorrectMethodCallException{

        //Must check if still in TOTEM_PLACING_PHASE
        if(this.currentRoundPhase != RoundPhasesEnum.TOTEM_PLACING) throw new IncorrectMethodCallException("setUpDrawingPhase", "Wrong phase");

        //Checks if TOTEM_PLACING_PHASE is finished
        if(!turnOrder.everybodyPlayed()) throw new IncorrectMethodCallException("setUpDrawingPhase", "Totem phase not finished");

        this.currentRoundPhase  = RoundPhasesEnum.ACTION_PHASE;
        observers.onGameRoundStatusUpdate(this);

        Optional<OfferCard> firstPlayer = board.getOfferCards().stream()
                    .filter(offerCard -> !offerCard.isFree()).findFirst();

        //Shouldn't happen but better checking
        if(!firstPlayer.isPresent()) throw new IllegalStateException("Cannot find players in offerTrack");

        OfferCard offerCard = firstPlayer.get();
        setUpPlayerActing(offerCard.getPlayer(), offerCard);

    }

    protected void setCurrentRoundPhase(RoundPhasesEnum currentRoundPhase){
        this.currentRoundPhase = currentRoundPhase;
        observers.onGameRoundStatusUpdate(this);
    }

    /**
     * Frees the current player's offer tile, returns their totem to the turn-order tile,
     * and sets the next player in line as the acting player.
     * Must only be called after the current player has finished drawing.
     *
     * @throws IncorrectMethodCallException if not in action phase or the current player has not finished drawing
     */
    public void setNextPlayerDrawing() throws IncorrectMethodCallException{

        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE) throw new IncorrectMethodCallException("setNextPlayerDrawing", "Wrong phase");
        if(!drawManager.hasFinishedDrawing()) throw new IncorrectMethodCallException("setNextPlayerDrawing", "Current player hasn't finished drawing");

        //Must free the Offer Card of the previous player
        Optional<OfferCard> previousPlayerOfferCard = board.getOfferCards().stream().filter(card -> !card.isFree()).findFirst();

        //This check should never fail but it is better to check
        if(!previousPlayerOfferCard.isPresent()) throw new IllegalStateException("Something went wrong in setNextPlayerDrawing()");

        //Frees the offerCard
        turnOrder.setPlayer(previousPlayerOfferCard.get().getPlayer());
        previousPlayerOfferCard.get().free();
        //OfferTrackUpdate?
        observers.onOfferTrackUpdate(board);

        Optional<OfferCard> nextOfferCard = board.getOfferCards().stream().filter(card -> !card.isFree()).findFirst();

        //If no player is found, the ACTION_PHASE has ended (no one is on the offer track)
        if(!nextOfferCard.isPresent()){
            return;
        }

        OfferCard offerCard = nextOfferCard.get();
        Player nextPlayer = offerCard.getPlayer();


        setUpPlayerActing(nextPlayer, offerCard);
        //update?


    }

    public void setUpBonusDrawingPhase() throws IncorrectMethodCallException{

        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE) throw new IncorrectMethodCallException("setUpBonusDrawingPhase", "Wrong phase");

        this.currentRoundPhase = RoundPhasesEnum.BONUS_DRAWING_PHASE;
        observers.onGameRoundStatusUpdate(this);

        Player playerWithBonus = players.stream().filter(player -> player.hasBonusDraw()).findFirst().orElse(null);

        if(playerWithBonus == null) drawManager.setUp(0, 0);
        else {
            this.playerActing = playerWithBonus;
            //This is also hard coded, but in the current status of Mesos only one player can have that building, and the
            //effects says that draws only 1 from top.
            drawManager.setUp(1, 0);
        }


    }

    public void setUpTotemPlacingPhase() throws IncorrectMethodCallException {
        if(currentRoundPhase != RoundPhasesEnum.END_TURN) throw new IncorrectMethodCallException("setUpTotemPlacingPhase" , "Wrong phase");
        this.currentRoundPhase = RoundPhasesEnum.TOTEM_PLACING;
        roundNumber++;
        observers.onGameRoundStatusUpdate(this);
    }


    public void playerSkipUpper(Player player) throws WrongPlayerTurnException, WrongRoundPhaseException,
            IllegalSkipException {
        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE && currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE)
            throw new WrongRoundPhaseException();
        if(!player.equals(playerActing)) throw new WrongPlayerTurnException();
        if(currentRoundPhase == RoundPhasesEnum.ACTION_PHASE && board.upperLineHasCharacters()) throw new
                IllegalSkipException(BoardRows.UPPER);
        drawManager.skipUpper();
    }

    public void playerSkipLower(Player player) throws WrongPlayerTurnException, WrongRoundPhaseException,
            IllegalSkipException {
        if(currentRoundPhase != RoundPhasesEnum.ACTION_PHASE && currentRoundPhase != RoundPhasesEnum.BONUS_DRAWING_PHASE)
            throw new WrongRoundPhaseException();
        if(!player.equals(playerActing)) throw new WrongPlayerTurnException();
        if(currentRoundPhase == RoundPhasesEnum.ACTION_PHASE && board.underLineHasCharacters()) throw new
                IllegalSkipException(BoardRows.LOWER);
        drawManager.skipLower();
    }

}
