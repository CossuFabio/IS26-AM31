package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.exceptions.*;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.RoundPhasesEnum;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.requests.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.JoinNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.TotemNetworkRequest;

import java.io.IOException;
import java.util.List;

public class GameController {

    private ResourceFinder resourceFinder;
    private Game game;

    public GameController(Game gameInstance) throws IOException{
        this.game = gameInstance;
        this.resourceFinder = new ResourceFinder(game);
    }


    public boolean isPlayerInGame(String nickname){
        for(Player p: game.getPlayersList())
            if(p.getNickname().equals(nickname))
                return true;
        return false;
    }

    //-----Requests handling-----
    public void handleAddPlayerMessage(JoinNetworkRequest request){

        Player newPlayer = new Player(request.getPlayerID(), request.getColor());

        try{
            game.addPlayer(newPlayer);

            if(game.getPlayersList().size() == game.getNumPlayers())
                game.gameStart();

        }catch(Exception e){
            //Client.sendError(e.getMessage());
        }

    }

    //Must add all exceptions and add the request source's connection in signature
    public void handleDraw(DrawNetworkRequest request){
        try{

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

                // Qui usiamo il metodo isBonusDrawPhaseFinished! Non avremo mai eccezioni.
                if (game.hasCurrentPlayerFinishedDrawing() && game.isBonusDrawPhaseFinished()) {
                    startEndGamePhase();
                }
            }

        }
        catch(Exception e){
            //client.notify(e.getMessage());
        }
    }



    //-----TOTEM PHASE-----
    //TODO: Test this
    public void handleTotemAction(TotemNetworkRequest request) {
        try{
            Player player = resourceFinder.getPlayerFromNickname(request.getPlayerID());
            OfferCard offerCard = resourceFinder.getOfferCard(request.getOfferTrackID());

            if(!game.isTotemPlacingPhaseFinished()) {
                game.totemChoiceAction(player, offerCard);
            }
            if(game.getTurnOrder().everybodyPlayed())
                startDrawPhase();
        } catch (Exception e) {
            //client.notify(message);
        }

    }

    public void startTotemPlacingPhase(){
        try{
            game.setUpTotemPlacingPhase();
        }
        catch (WrongRoundPhaseException e) {System.out.println(e.getMessage());}
        catch(IllegalStateException e) {System.out.println(e.getMessage());}
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
        catch (WrongRoundPhaseException e) {System.out.println(e.getMessage());}
        catch(IllegalStateException | IllegalAccessException e) {System.out.println(e.getMessage());}
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

        }catch (WrongRoundPhaseException e) {System.out.println(e.getMessage());}

    }

     //-----Endgame methods-----
    public void handleEndGame(){
        try{
            List<Player> leaderboard = game.gameEnd();
            //Broadcast clients the results of the winners
        }catch(WrongRoundPhaseException e){System.out.println(e.getMessage());}

    }
    @Override
    public String toString () {
        return "N. of active players " + game.getPlayersList().size() + "\n N. needed players " + game.getNumPlayers();
    }
    public Game getGame () {
        return this.game;
    }

}
