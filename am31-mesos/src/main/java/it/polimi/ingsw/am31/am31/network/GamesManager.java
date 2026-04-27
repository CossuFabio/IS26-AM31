package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.BoardRows;
import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.GameException;
import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.LobbyException;
import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.LobbyNotFoundException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.PlayerAlreadyInGameException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.BadNetworkRequestException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.Board;
import it.polimi.ingsw.am31.am31.modelPackage.observerPattern.GameObserver;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorCategory;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessage;
import it.polimi.ingsw.am31.am31.network.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequestFactory;
import it.polimi.ingsw.am31.am31.network.requests.RequestMethodsConstants;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.updateMessages.UpdateFactory;
import it.polimi.ingsw.am31.am31.network.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

        //GamesManager accepts only these commands. They are filtered from the Server
        commands.put(RequestMethodsConstants.METHOD_NEW_GAME, this::createGame);
        commands.put(RequestMethodsConstants.METHOD_DRAW, this::drawCard);
        commands.put(RequestMethodsConstants.METHOD_JOIN_GAME, this::joinGame);
        commands.put(RequestMethodsConstants.METHOD_SHOW_LOBBIES, this::showLobbies);
        commands.put(RequestMethodsConstants.METHOD_PLACE_TOTEM, this::placeTotem);

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

    public Set<Map.Entry<Integer, GameController>> getActiveGames() {
        return games.entrySet();
    }




    //Game methods TODO Fix concurrency problem
    private void createGame(NetworkRequest req, VirtualView view){
        try{
            //No validity checks required on specReq fields
            NewGameNetworkRequest specReq = (NewGameNetworkRequest) req;

            //Game creation
            GameResources gameResources = new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier());
            Game gameInstance = new Game (((NewGameNetworkRequest) req).getNumPlayers(), gameResources);

            //Controller creation and insert into map
            Integer id = nextGameID.getAndIncrement();
            GameController gameController = new GameController(gameInstance, id);

            if(playerToGame.putIfAbsent(specReq.getPlayerID(), gameController) != null) {
                view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(
                        new PlayerAlreadyInGameException(specReq.getPlayerID())));
                return;
            }

            games.put(id, gameController);

            NetworkObserver newPlayerObs = new NetworkObserver(view, specReq.getPlayerID());
            JoinGameNetworkRequest fakeJoinReq = new JoinGameNetworkRequest(specReq.getColor(), id);
            gameController.handleAddPlayerMessage(fakeJoinReq, newPlayerObs);




        }catch(IOException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException("Unable to create game")));
        }catch(GameException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }
        catch(GameInvariantException e){
            System.err.println("Game invariant violated: " + e.getMessage());
        }catch(Exception e){
            //For others uncatched exceptions
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
        }catch(GameInvariantException e){
            System.err.println("Game invariant violated: " + e.getMessage());
        }catch(Exception e){
            //For uncatched exceptions
            System.err.println(e.getMessage());
        }


    }


    private void joinGame(NetworkRequest req, VirtualView view) {                                                                                                       JoinGameNetworkRequest joinReq = (JoinGameNetworkRequest) req;
        GameController gameToJoin = games.get(joinReq.getGameID());
        if (gameToJoin == null) {
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new LobbyNotFoundException(joinReq.getGameID())));
            return;
        }

        //putIfAbsent reservers the spot for the insert. If we don't to this, this can happen:
        // - check if a player is not in a game and get success, then the thread is preempted
        // - another request from the same nickname arrives and gets success
        // - now both request can register with the same nickname via playerToGame.put.
        // This happend because the first check and the but are not atomic operations. Using putIfAbsent makes
        // this operation atomic and saves us.
        // The prior insert is then correct if the insert of the player throws an error via playertogame.remove in catch
        // blocks.
        if (playerToGame.putIfAbsent(joinReq.getPlayerID(), gameToJoin) != null) {
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(
                    new PlayerAlreadyInGameException(joinReq.getPlayerID())));
            return;
        }

        try {
            gameToJoin.handleAddPlayerMessage(joinReq, new NetworkObserver(view, joinReq.getPlayerID()));
        } catch (GameException e) {
            playerToGame.remove(joinReq.getPlayerID(), gameToJoin);  // rollback and correct
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        } catch (GameInvariantException e) {
            playerToGame.remove(joinReq.getPlayerID(), gameToJoin);  // rollback and correct
            System.err.println(e.getMessage());
        }
    }

    private void showLobbies(NetworkRequest req, VirtualView view){

       try{
           //Nothing to verify
           ShowLobbyNetworkRequest specReq = (ShowLobbyNetworkRequest) req;
           ShowLobbyUpdate response = UpdateFactory.createShowLobbyUpdate(this);
           view.receiveUpdate(response);
       }catch(Exception e){
            System.err.println(e.getMessage());
       }
    }

    private void placeTotem(NetworkRequest req, VirtualView view){
        try{

            TotemNetworkRequest totemNetworkRequest = (TotemNetworkRequest) req;
            GameController controller = findGameFromPlayerUsername(req.getPlayerID());
            controller.handleTotemAction(totemNetworkRequest);

        }catch(NetworkException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameInvariantException e){
            System.err.println(e.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }



    public void handleDisconnect(String playerID){

        //Doesn't use findGameFromPlayerUsername because throws different exception than what is needed here
        GameController gameController = playerToGame.remove(playerID);
        if(gameController == null) return; //Nothing to handle here

        //If game hasn't started yet, just remove the player

        boolean isGameStillActive = gameController.handleDisconnection(playerID);
        if(isGameStillActive) return;

        Integer gameId = gameController.getGameID();
        playerToGame.values().removeIf(v -> v == gameController);
        games.remove(gameId);


    }

}
