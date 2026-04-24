package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateFactory;

public class NetworkObserver implements GameObserver {

    private final VirtualView virtualView;

    public NetworkObserver(VirtualView virtualView){
        this.virtualView = virtualView;
    }

    @Override
    public void onPlayerNewBuildingEvent(Player player) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createPlayerBuildingsUpdate(player));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onPlayerScoresUpdate(Player player) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createPlayerScoresUpdate(player));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onPlayerTribeUpdate(Player player) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createPlayerTribeUpdate(player));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createGameRoundStatusUpdate(game));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onPlayersListUpdate(Game game) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createPlayersListUpdate(game));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createCardLineUpdate(board, row));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onOfferTrackUpdate(Board board) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createOfferTrackUpdate(board));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void onTurnOrderUpdate(Board board) {
        try{
            virtualView.receiveUpdate(UpdateFactory.createTurnOrderUpdate(board));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void onGameStartUpdate () {
        try{
            virtualView.receiveUpdate(UpdateFactory.createGameStartUpdate());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
