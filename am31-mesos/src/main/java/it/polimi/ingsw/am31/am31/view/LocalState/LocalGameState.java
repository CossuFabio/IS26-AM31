package it.polimi.ingsw.am31.am31.view.LocalState;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.network.ClientConfig;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.PlayerMessage;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.serverMessages.SuccessRegistrationUpdate;


import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color.RED;

public class LocalGameState implements LocalObservable {


    private int era;
    private RoundPhasesEnum currentRoundPhase;
    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private LocalBoardState board;
    private LocalObserver gameObserver; //only one, its the players view
    private List<String> turnorder;
    public LocalGameState () {
        players = new ArrayList<LocalPlayerState>();
        board = new LocalBoardState();

        //Prevent nullpointerexc
        //TODO: Insert dummy observer

        turnorder = new ArrayList<>();
        int roundNumber = 0;
        //DUMMY
        playerActing = new LocalPlayerState("dummy", RED);

        currentRoundPhase = RoundPhasesEnum.GAME_STARTING;
    }


//this is the only observable object client side
@Override
    public void addObserver(LocalObserver obs) {
        gameObserver=obs;
    }


//updates before start of the game
    public void GameStart() {
        //calls on its observer that the game has started
        gameObserver.onGameStartUpdate();
    }
    public void ShowLobby(List<LobbyDescriptor> lobbies){
        //sends the view the lobbies
        gameObserver.onShowLobbyUpdate(lobbies);
    }
    public void successRegistration(SuccessRegistrationUpdate msg){
        gameObserver.onSuccessRegistration(msg);
    }



    //setter methods, called by the connection when it receives updates.
    public void setPlayerActing() {

    }
    public void setCurrentRoundPhase(RoundPhasesEnum newPhase) {
        this.currentRoundPhase=newPhase;
        gameObserver.onRoundPhaseUpdate();
    }
    public void setPlayers(List<PlayerMessage> playersList){
        //remakes the list everytime

        //k
        players = playersList.stream().map(p-> new LocalPlayerState(p.getNickname(), p.getColor())).toList();
        gameObserver.onPlayerListUpdate();
        //once final this really should never change
    }
    public void setCardLine(List<Card> cards, BoardRows row){
        if(row.equals(BoardRows.LOWER))
            board.setUnderLine(cards);
        else if(row.equals(BoardRows.UPPER))
            board.setUpperLine(cards);
        gameObserver.onCardLineUpdate();
    }
    public void setEra(int era){
        this.era=era;
        gameObserver.onEraUpdate();
    }
    public void setRoundNumber(int newRound){
        this.roundNumber=newRound;
        gameObserver.onRoundNumberUpdate();
    }
    public void setOfferTrack(ArrayList<LocalOfferCard> offerTrack){
//updates the offerTrack, after every totem placement
        board.setOfferTrack(offerTrack);
        gameObserver.onOfferTrackUpdate();
    }

    public void updatePlayerScore(String id,int newfood, int newpp){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
            {
                //finds the player, udpates scores
                p.setFood(newfood);
                p.setPrestigePoints(newpp);
        }
        gameObserver.onPlayerScoreUpdate();
        //
    }
    public void updatePlayerBuildings(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setBuildings(cards);
        gameObserver.onPlayerTribeUpdate();
        //
    }
    public void updatePlayerTribe(String id, List<Card> cards){
        for(LocalPlayerState p: players)
            if(p.getNickname().equals(id))
                p.setTribe(cards);
        gameObserver.onPlayerTribeUpdate();
        //
    }
    public void setTurnOrder(List<String> newturnorder){
        //makes new turnorder, adds all names
        turnorder = new ArrayList<>();
        turnorder.addAll(newturnorder);

        gameObserver.onTurnOrderUpdate();
        //
    }
    public void removeObserver(LocalObserver obs) {
        if (gameObserver == obs)
            gameObserver = null;
    }
    public void receiveLobbyError(String errorMessage){
        //redirects to view
        gameObserver.onLobbyError(errorMessage);
        //error implemented differently by tui and gui
    }


//getters, used by TUI / GUI to draw
    public RoundPhasesEnum getCurrentRoundPhase(){return currentRoundPhase;}
    public int getRoundNumber(){return roundNumber;}
    public int getEra(){return era;}
    public LocalBoardState getBoard(){return board;}
    public String getPlayerActing (){return turnorder.getFirst();}
    public List<String> getTurnorder(){return turnorder;}


}