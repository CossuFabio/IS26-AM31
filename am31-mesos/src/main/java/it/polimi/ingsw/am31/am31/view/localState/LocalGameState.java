package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;


import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.RED;

/**
 * Represents the local state of the game, used by the client to display game information.
 */
public class LocalGameState{


    private int era;
    private RoundPhasesEnum currentRoundPhase;

    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private final LocalBoardState board;
    private List<Card> eventsSolved;
    //Will be fixed to numPlayers size
    private List<LocalPlayerState> turnOrder;

    //Will be set to true once the game starts. Will be set to false when resetting LocalGameState
    private boolean isValidState = false;

    //Used only at the end of the game
    private List<LocalLeaderBoard> leaderboard;
    private List<GlobalRankingEntry> globalRanking;

    /**
     * Constructs a new LocalGameState and initializes default values to prevent NullPointerExceptions.
     */
    public LocalGameState () {
        //Creations to prevent NullPointersException
        this.leaderboard = new ArrayList<>();
        this.globalRanking = new ArrayList<>();
        players = new ArrayList<LocalPlayerState>();
        board = new LocalBoardState();
        turnOrder = new ArrayList<>();
        eventsSolved = new ArrayList<>();
        //DUMMY
        playerActing = new LocalPlayerState("dummy", RED);
        currentRoundPhase = RoundPhasesEnum.GAME_STARTING;
    }


    /**
     * Sets the game state to valid, indicating the game has started.
     */
    public void gameStart() {
        isValidState = true;
    }


    /**
     * Sets the current round phase. If switching from TOTEM_PLACING to ACTION_PHASE,
     * the list of solved events is cleared.
     * @param newPhase The new round phase.
     */
    public void setCurrentRoundPhase(RoundPhasesEnum newPhase) {
        if(currentRoundPhase.equals(RoundPhasesEnum.TOTEM_PLACING)&& newPhase.equals(RoundPhasesEnum.ACTION_PHASE))
            //when we switch from totem to action, we empty the list 
            eventsSolved = new ArrayList<Card>();
        this.currentRoundPhase=newPhase;
    }

    /**
     * Sets the list of players in the game.
     * @param playersList The new list of players.
     */
    public void setPlayers(List<LocalPlayerState> playersList){
        this.players = playersList;
    }
    /**
     * Sets the cards for a specific row on the board.
     * @param cards The list of cards to set.
     * @param row The row (UPPER or LOWER) to set the cards for.
     */
    public void setCardLine(List<Card> cards, BoardRows row){
        if(row.equals(BoardRows.LOWER))
            board.setUnderLine(cards);
        else if(row.equals(BoardRows.UPPER))
            board.setUpperLine(cards);
    }
    /**
     * Sets the current era of the game.
     * @param era The new era number.
     */
    public void setEra(int era){
        this.era=era;
    }
    /**
     * Sets the current round number.
     * @param newRound The new round number.
     */
    public void setRoundNumber(int newRound){
        this.roundNumber=newRound;
    }
    /**
     * Sets the offer track on the board.
     * @param offerTrack The new list of offer cards.
     */
    public void setOfferTrack(List<LocalOfferCard> offerTrack){
        //updates the offerTrack, after every totem placement
        board.setOfferTrack(offerTrack);
    }

    /**
     * Updates the food and prestige points for a specific player.
     * @param id The nickname of the player to update.
     * @param newfood The player's new food count.
     * @param newpp The player's new prestige points.
     */
    public void updatePlayerScore(String id,int newfood, int newpp){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
            {
                //finds the player, udpates scores
                p.setFood(newfood);
                p.setPrestigePoints(newpp);
        }
    }

    /**
     * Updates the buildings for a specific player.
     * @param id The nickname of the player to update.
     * @param cards The player's new list of building cards.
     */
    public void updatePlayerBuildings(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setBuildings(cards);
    }

    /**
     * Updates the tribe cards for a specific player.
     * @param id The nickname of the player to update.
     * @param cards The player's new list of tribe cards.
     */
    public void updatePlayerTribe(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setTribe(cards);
    }

    /**
     * Updates the bonus draw status for a specific player.
     * @param playerId The nickname of the player to update.
     * @param hasBonus True if the player has a bonus draw, false otherwise.
     */
    public void updatePlayerBonusDraw(String playerId, boolean hasBonus){
        for(LocalPlayerState p : players)
            if(p.getNickname().equals(playerId))
                p.setBonusDraw(hasBonus);
    }

    /**
     * Sets the turn order for the players and updates the player acting.
     * @param newTurnOrder The new list of players in turn order.
     */
    public void setTurnOrder(List<LocalPlayerState> newTurnOrder){
        this.turnOrder = newTurnOrder;
        setPlayerActing(newTurnOrder.getFirst());
    }

    /**
     * Sets the player who is currently acting.
     * @param p The player who is currently acting.
     */
    public void setPlayerActing(LocalPlayerState p){
        this.playerActing = p;
    }
    /**
     * Sets the local leaderboard.
     * @param leaderboard The new leaderboard.
     */
    public void setLeaderboard(List<LocalLeaderBoard> leaderboard){this.leaderboard = leaderboard;}
    /**
     * Sets the global ranking.
     * @param globalRanking The new global ranking.
     */
    public void setGlobalRanking(List<GlobalRankingEntry> globalRanking){this.globalRanking = globalRanking;}
    /**
     * Returns the global ranking.
     * @return A list of GlobalRankingEntry objects.
     */
    public List<GlobalRankingEntry> getGlobalRanking(){return globalRanking;}

    /**
     * Adds a player to the list of players (testing only)
     * @param p The player to add.
     */
    public void addPlayer(LocalPlayerState p){
        players.add(p);
    }

    /**
     * Resets the local game state to its initial values.
     */
    public void reset(){
        isValidState = false;
        players = new ArrayList<>();
        board.reset();
        turnOrder = new ArrayList<>();
        this.roundNumber = 0;
        this.era = 0;
        this.eventsSolved = new ArrayList<>();
        this.leaderboard = new ArrayList<>();
        this.globalRanking = new ArrayList<>();
        playerActing = new LocalPlayerState("dummy", RED);
        currentRoundPhase = RoundPhasesEnum.GAME_STARTING;
    }


    /**
     * Returns the list of solved event cards.
     * @return A list of Card objects representing solved events.
     */
    public List<Card> getEventsSolved(){
        return eventsSolved;
    }
    /**
     * Returns the current round phase.
     * @return The current RoundPhasesEnum.
     */
    public RoundPhasesEnum getCurrentRoundPhase(){return currentRoundPhase;}

    /**
     * Returns the current round number.
     * @return The current round number.
     */
    public int getRoundNumber(){return roundNumber;}

    /**
     * Returns the current era.
     * @return The current era number.
     */
    public int getEra(){return era;}

    /**
     * Returns the local board state.
     * @return The LocalBoardState object.
     */
    public LocalBoardState getBoard(){return board;}

    /**
     * Determines and returns the player who is currently acting based on the current round phase.
     * @return The LocalPlayerState of the acting player, or null if no player is acting.
     */
    public LocalPlayerState getPlayerActing (){
        if(currentRoundPhase.equals(RoundPhasesEnum.TOTEM_PLACING)) {
            for (LocalPlayerState p : turnOrder)
                if (!(p == null))
                    return p;
        }
        else if (currentRoundPhase.equals(RoundPhasesEnum.ACTION_PHASE)) {
            for(LocalOfferCard c: board.getOfferTrack())
                if(!c.isFree())
                    for(LocalPlayerState d: players)
                        if(d.getNickname().equals(c.getPlayer()))
                            return d;
        }

        else if(currentRoundPhase.equals(RoundPhasesEnum.BONUS_DRAWING_PHASE)){
            return players.stream().filter(LocalPlayerState::hasBonusDraw).findFirst().orElse(null);
        }
        return null;
    }
    /**
     * Adds a solved event card to the beginning of the list.
     * @param e The event card to add.
     */
    public void addSolvedEvent(Card e){
        eventsSolved.addFirst(e); 
    }

    /**
     * Returns the current turn order.
     * @return A list of LocalPlayerState objects in turn order.
     */
    public List<LocalPlayerState> getTurnOrder(){
            return turnOrder;}
    /**
     * Returns the list of all players in the game.
     * @return A list of LocalPlayerState objects.
     */
    public List<LocalPlayerState> getPlayers(){return players;}
    /**
     * Finds a player by their nickname.
     * @param nickname The nickname of the player to find.
     * @return The LocalPlayerState object if found, otherwise null.
     */
    public LocalPlayerState findPlayer(String nickname){
        return players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
    }

    /**
     * Returns the local leaderboard.
     * @return A list of LocalLeaderBoard objects.
     */
    public List<LocalLeaderBoard> getLeaderboard(){return leaderboard; }

}