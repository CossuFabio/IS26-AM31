package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.BoardRows;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.TurnOrder;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.UpdateFactory;
import it.polimi.ingsw.am31.am31.network.messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link GameObserver} class that wraps a {@link VirtualView} and forwards model updates to the client
 * as network messages. Sends asynchronously to avoid blocking the game thread, especially on RMI.
 */
public class NetworkObserver implements GameObserver {

    private final VirtualView virtualView;
    private final String identifier;
    private final ExecutorService executors;

    /**
     * @param virtualView the client-side view to forward updates to
     * @param identifier the player's username, used to identify this observer
     */
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
    public void onPlayerBonusDrawUpdate(Player player) {
        executors.submit(()->{
            try {
                virtualView.receiveUpdate(UpdateFactory.createPlayerBonusDrawUpdate(player));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }});
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
    public void onGameEndUpdate(Game game, List<GlobalRankingEntry> globalRanking) {
        executors.submit(()-> {try{
            virtualView.receiveUpdate(UpdateFactory.createGameEndUpdate(game, globalRanking));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }});
    }

    @Override
    public void onGameEventResolveUpdate(EventCard event) {
        executors.submit(()->{try{
            virtualView.receiveUpdate(UpdateFactory.createGameEventResolveUpdate(event));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }});
    }
}
