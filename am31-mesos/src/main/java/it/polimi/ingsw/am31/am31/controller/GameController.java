package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.exceptions.InvalidDrawException;
import it.polimi.ingsw.am31.am31.exceptions.InvalidResourceException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.IPickable;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.network.requests.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;

import java.io.IOException;

public class GameController {
    private ResourceFinder resourceFinder;
    private Game game;

    public GameController(Game gameInstance) throws IOException{
        this.game = gameInstance;
        this.resourceFinder = new ResourceFinder(game);
    }


    //TODO: Implement this, determine messages player needs to send to join
    public void handleAddPlayerMessage(NetworkRequest request){

    }


    //TODO: Implement this, determine messages player needs to send to join
    public void handleTotemAction(NetworkRequest request){

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

            //continue round flow execution
            //if(game.hasCurrentPlayerFinishedDrawing())
            //   game.setNextPlayerDrawing()
            //if(game.getPhase == ACTION_PHASE && game.everyBodyDrew())
            //   game.setPhase(BONUS_DRAW);
            //   game.setUpBonusDrawPhase()
            //if(game.getPhase == BONUS_DRAW_PHASE && game.hasCurrentPlayerFinishedDrawing())
            //   game.setPhase(END_ROUND)
            //   game.endRound()
        }
        catch(Exception e){
            //client.notify(e.getMessage());
        }
    }



}
