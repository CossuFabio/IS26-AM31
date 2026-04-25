package it.polimi.ingsw.am31.am31.view;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.BuildingDeck;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.Deck;
import it.polimi.ingsw.am31.am31.modelPackage.deckFolder.TribeDeck;
import it.polimi.ingsw.am31.am31.network.updateMessages.boardUpdates.OfferCardMessage;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.LobbyDescriptor;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalBoardState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObservable;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;

import java.util.ArrayList;
import java.util.List;

public class LocalGameState implements LocalObservable {
    private int era;
    private RoundPhasesEnum currentRoundPhase;
    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private LocalBoardState board;
    private LocalObserver gameObserver; //only one, its the players view
    private BuildingDeck buildings;
    private TribeDeck cards;
    public LocalGameState () {
        players = new ArrayList<LocalPlayerState>();
    }


//this is the only observable object client side
@Override
    public void addObserver(LocalObserver obs) {
        gameObserver=obs;
    }

    public void GameStart() {
        //calls on its observer that the game has started
        gameObserver.onGameStartUpdate();
    }
    public void ShowLobby(List<LobbyDescriptor> lobbies){
        //sends the view the lobbies
        gameObserver.onShowLobbyUpdate(lobbies);
    }

    //setter methods, called by the connection when it receives updates.
    public void setPlayerActing() {

    }
    public void setCurrentRoundPhase(RoundPhasesEnum newPhase) {
        this.currentRoundPhase=newPhase;
        gameObserver.onRoundPhaseUpdate();
    }
    public void setPlayers(List<String> playersList){
        int i=0;
        for(String p : playersList)
            players.set(i++, new LocalPlayerState(p));
        gameObserver.onPlayerListUpdate();
    }
    public void setCardLine(List<String> cardIds, BoardRows row){
        if(row.equals(BoardRows.LOWER))
            board.setUnderLine();
        else if(row.equals(BoardRows.UPPER))
            board.setUpperLine();
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
    public void setOfferTrack(List<OfferCardMessage> offerTrack){

    }
}