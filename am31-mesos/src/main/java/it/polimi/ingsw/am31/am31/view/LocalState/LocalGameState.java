package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;


import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.RED;

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




    public void gameStart() {
        isValidState = true;
    }


    //setter methods, called by the connection when it receives updates.
    public void setCurrentRoundPhase(RoundPhasesEnum newPhase) {
        this.currentRoundPhase=newPhase;
    }

    public void setPlayers(List<LocalPlayerState> playersList){
        this.players = playersList;
    }
    public void setCardLine(List<Card> cards, BoardRows row){
        if(row.equals(BoardRows.LOWER))
            board.setUnderLine(cards);
        else if(row.equals(BoardRows.UPPER))
            board.setUpperLine(cards);
    }
    public void setEra(int era){
        this.era=era;
    }
    public void setRoundNumber(int newRound){
        this.roundNumber=newRound;
    }
    public void setOfferTrack(List<LocalOfferCard> offerTrack){
        //updates the offerTrack, after every totem placement
        board.setOfferTrack(offerTrack);
    }

    public void updatePlayerScore(String id,int newfood, int newpp){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
            {
                //finds the player, udpates scores
                p.setFood(newfood);
                p.setPrestigePoints(newpp);
        }
    }

    public void updatePlayerBuildings(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setBuildings(cards);
    }

    public void updatePlayerTribe(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setTribe(cards);
    }

    public void updatePlayerBonusDraw(String playerId, boolean hasBonus){
        for(LocalPlayerState p : players)
            if(p.getNickname().equals(playerId))
                p.setBonusDraw(hasBonus);
    }


    public void setTurnOrder(List<LocalPlayerState> newTurnOrder){
        this.turnOrder = newTurnOrder;
        setPlayerActing(newTurnOrder.getFirst());
    }

    public void setPlayerActing(LocalPlayerState p){
        this.playerActing = p;
    }
    public void setLeaderboard(List<LocalLeaderBoard> leaderboard){this.leaderboard = leaderboard;}
    public void setGlobalRanking(List<GlobalRankingEntry> globalRanking){this.globalRanking = globalRanking;}
    public List<GlobalRankingEntry> getGlobalRanking(){return globalRanking;}

    //for testing
    public void addPlayer(LocalPlayerState p){
        players.add(p);
    }

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



    //getters, used by TUI / GUI to draw
    public List<Card> getEventsSolved(){
        return eventsSolved;
    }
    public RoundPhasesEnum getCurrentRoundPhase(){return currentRoundPhase;}

    public int getRoundNumber(){return roundNumber;}

    public int getEra(){return era;}

    public LocalBoardState getBoard(){return board;}

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
    //adds the last event solved on top of the list.
    public void addSolvedEvent(Card e){
        eventsSolved.addFirst(e); 
    }

    public List<LocalPlayerState> getTurnOrder(){
            return turnOrder;}
    public List<LocalPlayerState> getPlayers(){return players;}
    public LocalPlayerState findPlayer(String nickname){
        return players.stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null);
    }

    public List<LocalLeaderBoard> getLeaderboard(){return leaderboard; }

}