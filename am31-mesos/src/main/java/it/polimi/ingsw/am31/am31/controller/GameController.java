package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidDrawException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidResourceException;
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

    public GameController(Game gameInstance){
        this.game = gameInstance;
        this.resourceFinder = new ResourceFinder(game);
        observerHandler = new GameObserversSet();
        game.addObserver(observerHandler);
    }


    public synchronized boolean isPlayerInGame(String nickname){
        if(nickname == null) return false;
        for(Player p: game.getPlayersList())
            if(p.getNickname().equals(nickname))
                return true;
        return false;
    }

    //-----Requests handling-----
    public synchronized void handleAddPlayerMessage(JoinGameNetworkRequest request, GameObserver obs) throws LobbyException, GameInvariantException {

        Player newPlayer = new Player(request.getPlayerID(), request.getColor());
        newPlayer.addObserver(observerHandler);
        game.addPlayer(newPlayer);
        observerHandler.addObserver(obs);
        if(game.getPlayersList().size() == game.getNumPlayers())
            game.gameStart();

    }


    public synchronized void handleDraw(DrawNetworkRequest request) throws IllegalActionException, GameInvariantException{
        Player player = resourceFinder.getPlayerFromNickname(request.getPlayerID());
        Card card = resourceFinder.getCardFromId(request.getCardID());

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
            if (game.hasCurrentPlayerFinishedDrawing() && game.isBonusDrawPhaseFinished()) {
                startEndGamePhase();
            }
        }

    }


    //-----TOTEM PHASE-----
    //TODO: Test this
    public synchronized void handleTotemAction(TotemNetworkRequest request) throws IllegalActionException, GameInvariantException{

        Player player = resourceFinder.getPlayerFromNickname(request.getPlayerID());
        OfferCard offerCard = resourceFinder.getOfferCard(request.getOfferTrackID());
        game.totemChoiceAction(player, offerCard);
        if(game.getTurnOrder().everybodyPlayed())
            startDrawPhase();
    }

    public synchronized void startTotemPlacingPhase() throws GameInvariantException{
        game.setUpTotemPlacingPhase();
    }


    //-----DRAW PHASES-----

    public synchronized void startDrawPhase() throws GameInvariantException{

        game.setUpDrawingPhase();
        //It is possible that the player doesn't need to draw
        while(game.hasCurrentPlayerFinishedDrawing() && !game.isDrawPhaseFinished()) {
            game.setNextPlayerDrawing();
        }

        if(game.isDrawPhaseFinished()) startBonusDrawPhase();

    }

    public synchronized void startBonusDrawPhase() throws GameInvariantException{
        game.setUpBonusDrawingPhase();
        if(game.isBonusDrawPhaseFinished()) startEndGamePhase();
    }

    //-----ROUND END-----
    public synchronized void startEndGamePhase() throws GameInvariantException {
        game.endRound();
        if(game.isGameFinished()) handleEndGame();
        else startTotemPlacingPhase();
    }

     //-----Endgame methods-----
    public synchronized void handleEndGame() throws GameInvariantException{
        List<Player> leaderboard = game.gameEnd();
        //Broadcast clients the results of the winners
    }

    public synchronized int getNumActivePlayers(){return game.getPlayersList().size(); }
    public synchronized int getNumPlayers(){return game.getNumPlayers();}

    
}
