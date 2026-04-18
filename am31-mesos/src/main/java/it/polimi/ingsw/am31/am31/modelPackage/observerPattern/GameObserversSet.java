package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.util.ArrayList;
import java.util.List;

public class GameObserversSet implements ObserverHandler{

    private final List<GameObserver> observers;

    public GameObserversSet(){
        this.observers = new ArrayList<>();
    }


    public void addObserver(GameObserver observer) {
        if(observer != null && !this.observers.contains(observer)) this.observers.add(observer);
    }

    public void removeObserver(GameObserver o ){
        observers.remove(o);
    }


    @Override
    public void onPlayerNewBuildingEvent(Player player) {
        observers.forEach(o -> onPlayerNewBuildingEvent(player));
    }

    @Override
    public void onPlayerScoresUpdate(Player player) {
        observers.forEach(o -> onPlayerScoresUpdate(player));
    }

    @Override
    public void onPlayerTribeUpdate(Player player) {
        observers.forEach(o -> onPlayerTribeUpdate(player));
    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {
        observers.forEach(o -> onGameRoundStatusUpdate(game));
    }

    @Override
    public void onPlayersListUpdate(Game game) {
        observers.forEach(o -> onPlayersListUpdate(game));
    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {
        observers.forEach(o -> onCardLineUpdate(board, row));
    }

    @Override
    public void onOfferTrackUpdate(Board board) {
        observers.forEach(o -> onOfferTrackUpdate(board));
    }

    @Override
    public void onTurnOrderUpdate(Board board) {
        observers.forEach(o -> onTurnOrderUpdate(board));
    }


}
