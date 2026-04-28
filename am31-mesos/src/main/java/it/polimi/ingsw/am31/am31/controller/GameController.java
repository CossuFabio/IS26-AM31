package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidDrawException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidResourceException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.GameNoLongerActiveException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserversSet;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.ObserverHandler;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;

import java.util.List;

public class GameController {

    private final ResourceFinder resourceFinder;
    private Game game;
    private final ObserverHandler observerHandler;
    private final Integer gameID;

    private volatile boolean isGameStillActive = true;

    public GameController(Game gameInstance, Integer gameID){
        this.game = gameInstance;
        this.resourceFinder = new ResourceFinder(game);
        observerHandler = new GameObserversSet();
        game.addObserver(observerHandler);
        this.gameID = gameID;
    }

    public Integer getGameID(){return this.gameID; }

    //-----Requests handling-----
    public synchronized void handleAddPlayerMessage(JoinGameNetworkRequest request, GameObserver obs) throws LobbyException, GameInvariantException {
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player newPlayer = new Player(request.getPlayerID(), request.getColor());
        newPlayer.addObserver(observerHandler);
        try{
            observerHandler.addObserver(obs);
            game.addPlayer(newPlayer);
            if(game.getPlayersList().size() == game.getNumPlayers())
                game.gameStart();
        }catch(Exception e){
            //Catch block remove the observer handler then propagate back the exception
            try { observerHandler.removeObserver(obs); } catch(Exception ignored){}
            try { game.removePlayer(newPlayer); } catch(Exception ignored){}
            throw e;
        }




    }


    public synchronized void handleDraw(DrawNetworkRequest request) throws IllegalActionException, GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player player = resourceFinder.getPlayerFromNickname(request.getPlayerID());
        Card card = resourceFinder.getCardFromId(request.getCardID());


        //Acceptable instanceof
        if(! (card instanceof IPickable)){
            throw new InvalidDrawException();
        }

        if(request.getBoardRows() == BoardRows.UPPER){
            game.playerDrawFromUpper(player, ((IPickable)card));
        }
        else if(request.getBoardRows() == BoardRows.LOWER){
            game.playerDrawFromLower(player, ((IPickable)card));
        }
        else{
            throw new InvalidResourceException("ROW");
        }


        if (game.getCurrentRoundPhase() == RoundPhasesEnum.ACTION_PHASE) {
            while(game.hasCurrentPlayerFinishedDrawing() && !game.isDrawPhaseFinished()) {
                game.setNextPlayerDrawing();
            }

            if(game.isDrawPhaseFinished()) {
                startBonusDrawPhase();
            }
        }
        else if (game.getCurrentRoundPhase() == RoundPhasesEnum.BONUS_DRAWING_PHASE) {

            //There can be only one player with bonus draw.
            //It is an hard-coded rule but it is easier to handle.
            if (game.hasCurrentPlayerFinishedDrawing() && game.isBonusDrawPhaseFinished()) {
                startEndGamePhase();
            }
        }

    }


    //-----TOTEM PHASE-----
    //TODO: Test this
    public synchronized void handleTotemAction(TotemNetworkRequest request) throws IllegalActionException, GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player player = resourceFinder.getPlayerFromNickname(request.getPlayerID());
        OfferCard offerCard = resourceFinder.getOfferCard(request.getOfferTrackID());
        game.totemChoiceAction(player, offerCard);
        if(game.getTurnOrder().everybodyPlayed())
            startDrawPhase();
    }

    public synchronized void startTotemPlacingPhase() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.setUpTotemPlacingPhase();
    }


    //-----DRAW PHASES-----

    public synchronized void startDrawPhase() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.setUpDrawingPhase();
        //It is possible that the player doesn't need to draw
        while(game.hasCurrentPlayerFinishedDrawing() && !game.isDrawPhaseFinished()) {
            game.setNextPlayerDrawing();
        }

        if(game.isDrawPhaseFinished()) startBonusDrawPhase();

    }

    public synchronized void startBonusDrawPhase() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.setUpBonusDrawingPhase();
        if(game.isBonusDrawPhaseFinished()) startEndGamePhase();
    }

    //-----ROUND END-----
    public synchronized void startEndGamePhase() throws GameInvariantException {
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.endRound();
        if(game.isGameFinished()) handleEndGame();
        else startTotemPlacingPhase();
    }

     //-----Endgame methods-----
    public synchronized void handleEndGame() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        List<Player> leaderboard = game.gameEnd();
        //Broadcast clients the results of the winners
    }

    public synchronized int getNumActivePlayers() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        return game.getPlayersList().size();
    }
    public synchronized int getNumPlayers() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        return game.getNumPlayers();
    }

    //Happens when a player disconnects. Returns true if the Game was still in starting phase
    public synchronized boolean handleDisconnection(String playerID){

        observerHandler.removeObserver(playerID);

        if(!game.isGameInStartingPhase()) {
            isGameStillActive = false;
            observerHandler.onGameCrashUpdate();
            return false;
        }


        try{
            Player player = resourceFinder.getPlayerFromNickname(playerID);
            game.removePlayer(player);

        }catch(PlayerNotFoundException e){
            System.err.println(e.getMessage());
        }



        return true;

    }
    
}
