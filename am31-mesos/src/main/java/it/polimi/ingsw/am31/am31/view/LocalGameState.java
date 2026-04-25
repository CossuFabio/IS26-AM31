package it.polimi.ingsw.am31.am31.view;

import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalBoardState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObservable;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalObserver;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;

import java.util.List;

public class LocalGameState implements LocalObservable {
    private int era;
    private RoundPhasesEnum currentRoundPhase;
    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private LocalBoardState board;
    private LocalObserver gameObserver; //only one, its the players view

    public LocalGameState () {

    }



@Override
    public void addObserver(LocalObserver obs) {
        gameObserver=obs;
    }

    public void GameStart() {
        //calls on its observer that the game has started
        gameObserver.onGameStartUpdate();
    }

    //setter methods, called by the connection when it receives updates.
    public void setPlayerActing() {}
    public void setCurrentRoundPhase() {}
    public void setPlayers(){}
    public void setBoard(){}
    public void setEra(){}
    public void setRoundNumber(){}
}