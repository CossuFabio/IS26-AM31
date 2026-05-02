package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.TurnOrder;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkObserver implements GameObserver {

    private final VirtualView virtualView;
    private final String identifier;
    private final ExecutorService executors;

    public NetworkObserver(VirtualView virtualView, String identifier){
        this.identifier = identifier == null ? "" : identifier;
        this.virtualView = virtualView;
        this.executors = Executors.newSingleThreadExecutor();
    }

    @Override
    public String getIdentifier(){
        return identifier;
    }

    @Override
    public void notifyRemoveMe() {
        executors.shutdown();
    }

    @Override
    public void onPlayerNewBuildingEvent(Player player) {
        executors.submit(()->{
        try {
            virtualView.receiveUpdate(UpdateFactory.createPlayerBuildingsUpdate(player));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onPlayerScoresUpdate(Player player) {

        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createPlayerScoresUpdate(player));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onPlayerTribeUpdate(Player player) {
        executors.submit(()->{
        try{
            virtualView.receiveUpdate(UpdateFactory.createPlayerTribeUpdate(player));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onGameRoundStatusUpdate(Game game) {
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createGameRoundStatusUpdate(game));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onPlayersListUpdate(Game game) {
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createPlayersListUpdate(game));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onCardLineUpdate(Board board, BoardRows row) {
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createCardLineUpdate(board, row));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onOfferTrackUpdate(Board board) {
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createOfferTrackUpdate(board));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onTurnOrderUpdate(TurnOrder turnorder) {
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createTurnOrderUpdate(turnorder));
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }

    public void onGameCrashUpdate(){
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createGameCrashUpdate());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }});
    }


    @Override
    public void onGameStartUpdate (Game game) {
        executors.submit(()->{try{
            //signals game start, then updates every part of the view
            for(Player p : game.getPlayersList()){
                onPlayerTribeUpdate(p);
                onPlayerScoresUpdate(p);
                //onPlayerNewBuildingEvent(p); idk about this one
            }
            //game status update
            onGameRoundStatusUpdate(game);
            //board updates
            onCardLineUpdate(game.getBoard(),BoardRows.UPPER);
            onCardLineUpdate(game.getBoard(),BoardRows.LOWER);
            onOfferTrackUpdate(game.getBoard());
            onTurnOrderUpdate(game.getTurnOrder());
            virtualView.receiveUpdate(UpdateFactory.createGameStartUpdate());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onGameEndUpdate(List<Player> leaderboard) {
        executors.submit(()-> {try{
            virtualView.receiveUpdate(UpdateFactory.createGameEndUpdate(leaderboard));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }});
    }
}
