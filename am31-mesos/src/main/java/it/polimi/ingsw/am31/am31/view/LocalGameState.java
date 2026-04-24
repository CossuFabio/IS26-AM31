package it.polimi.ingsw.am31.am31.view;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObservable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;

import java.util.List;

public class LocalGameState implements GameObservable {
    private int era;
    private RoundPhasesEnum currentRoundPhase;
    private LocalPlayerState playerActing;
    private int roundNumber;
    private List<LocalPlayerState> players;
    private LocalBoardState board;
    private GameObserver gameObserver; //only one

    public LocalGameState () {
    }


    @Override
    public void addObserver(ObserverHandler obs) {
        gameObserver=obs;
    }

    public void GameStart(LocalGameState newGame) {
    gameObserver.onGameStartUpdate(newGame);
    }

    public void copyfromgame(Game game){
        this.currentRoundPhase = game.getCurrentRoundPhase();
        //TODO FINISHI THIS
    }
}