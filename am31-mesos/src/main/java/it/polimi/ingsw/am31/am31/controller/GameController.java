package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.*;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.PlayerColorAlreadyTakenException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.TooManyPlayersException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.UsernameAlreadyTakenException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EmptyDeckException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.EverybodyPlayedException;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.InsufficientPlayersNumberException;
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
import it.polimi.ingsw.am31.am31.network.requests.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.JoinNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.TotemNetworkRequest;

import java.io.IOException;
import java.util.List;

public class GameController {

    private final ResourceFinder resourceFinder;
    private Game game;
    private final ObserverHandler observerHandler;

    public GameController(Game gameInstance) throws IOException{
        this.game = gameInstance;
        this.resourceFinder = new ResourceFinder(game);
        observerHandler = new GameObserversSet();
        game.addObserver(observerHandler);
    }


    public boolean isPlayerInGame(String nickname){
        for(Player p: game.getPlayersList())
            if(p.getNickname().equals(nickname))
                return true;
        return false;
    }

    //-----Requests handling-----
    public void handleAddPlayerMessage(JoinNetworkRequest request, GameObserver obs) throws LobbyException, IllegalActionException {

        Player newPlayer = new Player(request.getPlayerID(), request.getColor());
        game.addPlayer(newPlayer);
        observerHandler.addObserver(obs);
        if(game.getPlayersList().size() == game.getNumPlayers())
            game.gameStart();

    }

    //Must add all exceptions and add the request source's connection in signature
    public void handleDraw(DrawNetworkRequest request) throws IllegalActionException{
        

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
    public void handleTotemAction(TotemNetworkRequest request) throws IllegalActionException{

        Player player = resourceFinder.getPlayerFromNickname(request.getPlayerID());
        OfferCard offerCard = resourceFinder.getOfferCard(request.getOfferTrackID());
        game.totemChoiceAction(player, offerCard);
        if(game.getTurnOrder().everybodyPlayed())
            startDrawPhase();
    }

    public void startTotemPlacingPhase() throws IllegalActionException{
        try{
            game.setUpTotemPlacingPhase();
        }catch(GameInvariantException e){
            System.err.println("Game invariant violated! ");
            e.printStackTrace();
        }


    }


    //-----DRAW PHASES-----

    public void startDrawPhase() {
        try {
            game.setUpDrawingPhase();
            //It is possible that the player doesn't need to draw
            while(game.hasCurrentPlayerFinishedDrawing() && !game.isDrawPhaseFinished()) {
                game.setNextPlayerDrawing();
            }

            if(game.isDrawPhaseFinished()) startBonusDrawPhase();

        }
        //These exceptions are caused by programmer, they should be notified inside server
        catch (WrongRoundPhaseException | IllegalStateException | IllegalAccessException e  ) {System.err.println(e.getMessage());}

    }

    public void startBonusDrawPhase(){
        try{

            game.setUpBonusDrawingPhase();
            if(game.isBonusDrawPhaseFinished()) startEndGamePhase();

        }catch(WrongRoundPhaseException e){System.out.println(e.getMessage());}
    }

    //-----ROUND END-----
    public void startEndGamePhase(){

        try{

            game.endRound();
            if(game.isGameFinished()) handleEndGame();
            else startTotemPlacingPhase();

        }catch (Exception e) {System.out.println(e.getMessage());}

    }

     //-----Endgame methods-----
    public void handleEndGame(){
        try{
            List<Player> leaderboard = game.gameEnd();
            //Broadcast clients the results of the winners
        }catch(WrongRoundPhaseException e){System.out.println(e.getMessage());}

    }

    public int getNumActivePlayers(){return game.getPlayersList().size(); }
    public int getNumPlayers(){return game.getNumPlayers();}

}
