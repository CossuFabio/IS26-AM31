package it.polimi.ingsw.am31.am31.modelPackage.observerPattern;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.VirtualView;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class GameObserversSet implements ObserverHandler{

    private final List<GameObserver> observers;
    private final ExecutorService executors;

    public GameObserversSet(){
        this.observers = new CopyOnWriteArrayList<>();
        this.executors = Executors.newSingleThreadExecutor();
    }


    public void addObserver(GameObserver observer) {
        if(observer != null && !this.observers.contains(observer)) this.observers.add(observer);
    }

    public void removeObserver(GameObserver o ){
        observers.remove(o);
    }


    @Override
    public void onPlayerNewBuildingEvent(Player player) {
        sendAsyncUpdate( () -> {observers.forEach(o -> o.onPlayerNewBuildingEvent(player));});
    }

    @Override
    public void onPlayerScoresUpdate(Player player) {
        sendAsyncUpdate( () -> {observers.forEach(o -> o.onPlayerScoresUpdate(player));});
    }

    @Override
    public void onPlayerTribeUpdate(Player player) {
        sendAsyncUpdate( () -> {observers.forEach(o -> o.onPlayerTribeUpdate(player));});
    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {
        sendAsyncUpdate( () -> {
            observers.forEach(o -> o.onGameRoundStatusUpdate(game));
        });
    }

    @Override
    public void onPlayersListUpdate(Game game) {
        sendAsyncUpdate( () -> {observers.forEach(o -> o.onPlayersListUpdate(game));});
    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {
        sendAsyncUpdate(() -> {observers.forEach(o -> o.onCardLineUpdate(board, row));});
    }

    @Override
    public void onOfferTrackUpdate(Board board) {
        sendAsyncUpdate( () -> {observers.forEach(o -> o.onOfferTrackUpdate(board));});
    }

    @Override
    public void onTurnOrderUpdate(Board board) {
        sendAsyncUpdate(()-> {
            observers.forEach(o -> o.onTurnOrderUpdate(board));
        });
    }
    @Override
    public void onGameStartUpdate(){}

    private void sendAsyncUpdate(Runnable updateFunc){
        executors.submit(updateFunc);
    }
}
