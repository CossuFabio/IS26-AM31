package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.GameException;
import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.BadNetworkRequestException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequestFactory;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

public class GamesManager {

    //Associates GameID with controller
    private final Map<Integer, GameController> games;

    //Associates PlayerID with game
    private final Map<String, GameController> playerToGame;

    //Used to create the next gameID concurrently
    private final AtomicInteger nextGameID;

    //Used for requests routing
    private final Map<String, BiConsumer<NetworkRequest, VirtualView>> commands;

    public  GamesManager() {
        this.games = new ConcurrentHashMap<>();
        this.playerToGame =  new ConcurrentHashMap<>();
        this.nextGameID = new AtomicInteger(1);
        this.commands = new ConcurrentHashMap<>();

        commands.put(RequestMethodsConstants.METHOD_NEW_GAME, this::createGame);
        commands.put(RequestMethodsConstants.METHOD_DRAW, this::drawCard);
        commands.put(RequestMethodsConstants.METHOD_JOIN_GAME, this::joinGame);
        //commands.put(RequestMethodsConstants.METHOD_SHOW_LOBBIES, this::showLobbies);


    }

    //Integrity is already checked by the server
    public void handleRequest(NetworkRequest request, VirtualView virtualView) {
        if(!commands.containsKey(request.getType())){
            virtualView.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException("Unknown type")));
        }
        else{
            commands.get(request.getType()).accept(request, virtualView);
        }

    }

    //Utilities
    private GameController findGameFromPlayerUsername(String username) throws BadNetworkRequestException{
        GameController game = playerToGame.get(username);
        if(game != null) return game;
        throw new BadNetworkRequestException("Game not found");
    }

    public Map<Integer, GameController> getActiveGames() {
        return games;
    }

    private void createGame(NetworkRequest req, VirtualView view){
        try{
            //No validity checks required on specReq fields
            NewGameNetworkRequest specReq = (NewGameNetworkRequest) req;

            //Game creation
            GameResources gameResources = new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier());
            Game gameInstance = new Game (((NewGameNetworkRequest) req).getNumPlayers(), gameResources);

            //Controller creation and insert into map
            GameController gameController = new GameController(gameInstance);
            Integer id = nextGameID.getAndIncrement();
            games.put(id, gameController);

            //TODO: Send player the CreationSuccessUpdate and join page? Or modify CreateGameReq with the color to add directly here

        }catch(IOException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException("Unable to create game")));
        }catch(GameException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(Exception e){
            //For uncatched exceptions
            System.err.println(e.getMessage());
        }

    }

    private void drawCard(NetworkRequest req, VirtualView view){
        try{
            //No validity check required
            DrawNetworkRequest specReq = (DrawNetworkRequest) req;
            String requestorId = req.getPlayerID();
            GameController gameController = findGameFromPlayerUsername(requestorId);
            gameController.handleDraw(specReq);


        }catch(NetworkException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }


    }

    private void joinGame(NetworkRequest req, VirtualView view){}

//TODO: Finish this
//
//    private void showLobbies(NetworkRequest req, VirtualView view){
//
//       try{
//            ShowLobbyNetworkRequest specReq = (ShowLobbyNetworkRequest) req;
//            //view.receiveUpdate(UpdateFactory.createShowLobbyUpdate(this));
//        //}catch(NetworkException e){
//        //    view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
//       // }
//    }

}
