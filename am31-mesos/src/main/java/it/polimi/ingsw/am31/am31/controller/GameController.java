package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.DebugObserver;
import it.polimi.ingsw.am31.am31.database.DataBaseConnectionFactory;
import it.polimi.ingsw.am31.am31.database.LeaderBoardDAO;
import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.IllegalActionException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidDrawException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidResourceException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.illegalActionException.InvalidResourceTypeEnum;
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
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.GlobalRankingEntry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController {

    private final ResourceFinder resourceFinder;
    private final Game game;
    private final ObserverHandler observerHandler;
    private final Integer gameID;

    private boolean isGameStillActive = true;

    public GameController(Game gameInstance, Integer gameID){
        this.game = gameInstance;
        this.resourceFinder = new ResourceFinder(game);
        observerHandler = new GameObserversSet();
        game.setObserverHandler(observerHandler);
        observerHandler.addObserver(new DebugObserver());
        this.gameID = gameID;
    }

    public Integer getGameID(){return this.gameID; }

    //-----External commands handling-----
    public synchronized void addPlayer(String playerNickname, Color color, GameObserver obs) throws LobbyException, GameInvariantException {
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player newPlayer = new Player(playerNickname, color);
        newPlayer.setObserverHandler(observerHandler);
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


    public synchronized void drawCard(String playerId, String cardId, BoardRows row) throws IllegalActionException, GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player player = resourceFinder.getPlayerFromNickname(playerId);
        Card card = resourceFinder.getCardFromId(cardId);

        if(card == null || !card.canBePicked()){
            throw new InvalidDrawException();
        }

        if(row == BoardRows.UPPER){
            game.playerDrawFromUpper(player, ((IPickable)card));
        }
        else if(row == BoardRows.LOWER){
            game.playerDrawFromLower(player, ((IPickable)card));
        }
        else{
            throw new InvalidResourceException(InvalidResourceTypeEnum.ROW);
        }

        advanceAfterDrawOrSkip();

    }


    private void advanceAfterDrawOrSkip() throws GameInvariantException {
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
    public synchronized void placeTotem(String playerId, String offerTrackID) throws IllegalActionException, GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player player = resourceFinder.getPlayerFromNickname(playerId);
        OfferCard offerCard = resourceFinder.getOfferCard(offerTrackID);
        game.totemChoiceAction(player, offerCard);
        if(game.getTurnOrder().everybodyPlayed())
            startDrawPhase();
    }

    private synchronized void startTotemPlacingPhase() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.setUpTotemPlacingPhase();
    }


    //-----DRAW PHASES-----

    private synchronized void startDrawPhase() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.setUpDrawingPhase();
        //It is possible that the player doesn't need to draw
        while(game.hasCurrentPlayerFinishedDrawing() && !game.isDrawPhaseFinished()) {
            game.setNextPlayerDrawing();
        }

        if(game.isDrawPhaseFinished()) startBonusDrawPhase();

    }

    private synchronized void startBonusDrawPhase() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.setUpBonusDrawingPhase();
        if(game.isBonusDrawPhaseFinished()) startEndGamePhase();
    }

    public synchronized void skipDraw(String playerId, BoardRows row) throws IllegalActionException,
            GameInvariantException {
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        Player player = resourceFinder.getPlayerFromNickname(playerId);

        if(row == BoardRows.UPPER) game.playerSkipUpper(player);
        else if(row == BoardRows.LOWER) game.playerSkipLower(player);
        else throw new InvalidResourceException(InvalidResourceTypeEnum.ROW);

        advanceAfterDrawOrSkip();
    }

    //-----ROUND END-----
    private synchronized void startEndGamePhase() throws GameInvariantException {
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.endRound();
        if(game.isGameFinished()) handleEndGame();
        else startTotemPlacingPhase();
    }

     //-----Endgame methods-----
    private synchronized void handleEndGame() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        game.gameEnd();
        List<GlobalRankingEntry> dbLeaderBoardEntries = new ArrayList<>();
        try(Connection connection = DataBaseConnectionFactory.getNewConnection()){

            LeaderBoardDAO leaderBoardDAO = new LeaderBoardDAO(connection);
            leaderBoardDAO.insertScores(game.getPlayersList());
            dbLeaderBoardEntries = leaderBoardDAO.getLeaderBoardPosition(game.getPlayersList());


        }catch(SQLException e){
            System.out.println("Unable to query database!");
        }
        observerHandler.onGameEndUpdate(game, dbLeaderBoardEntries);
        isGameStillActive = false;
    }

    public synchronized int getNumActivePlayers() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        return game.getPlayersList().size();
    }
    public synchronized int getNumPlayers() throws GameInvariantException{
        if(!isGameStillActive) throw new GameNoLongerActiveException();
        return game.getNumPlayers();
    }

    //Happens when a player disconnects. Returns true if the Game was still in starting phase and the lobby isn't
    //empty after the last disconnection
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

        if(game.getPlayersList().isEmpty()){
            isGameStillActive = false;
            return false;
        }


        return true;

    }

    public synchronized List<Color> getAvailableColors(){
        List<Color> availableColors = new ArrayList<>(Arrays.asList(Color.values()));
        game.getPlayersList().forEach(p -> availableColors.remove(p.getColor()));
        return availableColors;
    }

    public synchronized boolean isGameFinished(){
        return game.isGameFinished();
    }

    public synchronized List<String> getPlayersId(){
        return game.getPlayersList().stream().map(Player::getNickname).toList();
    }


}
