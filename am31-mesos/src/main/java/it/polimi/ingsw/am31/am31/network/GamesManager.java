package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.GameException;
import it.polimi.ingsw.am31.am31.exceptions.GameInvariantException;
import it.polimi.ingsw.am31.am31.exceptions.NetworkException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.LobbyNotFoundException;
import it.polimi.ingsw.am31.am31.exceptions.gameException.lobbyException.PlayerAlreadyInGameException;
import it.polimi.ingsw.am31.am31.exceptions.networkException.BadNetworkRequestException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.resources.GameResources;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.network.Messages.errorMessage.ErrorMessageFactory;
import it.polimi.ingsw.am31.am31.network.requests.NetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.DrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.SkipDrawNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.gameRequest.TotemNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.JoinGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.NewGameNetworkRequest;
import it.polimi.ingsw.am31.am31.network.requests.lobbyRequest.ShowLobbyNetworkRequest;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.UpdateFactory;
import it.polimi.ingsw.am31.am31.network.Messages.updateMessages.gameUpdatesMessage.ShowLobbyUpdate;

import java.io.IOException;
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
        commands.put(NewGameNetworkRequest.METHOD, this::createGame);
        commands.put(DrawNetworkRequest.METHOD, this::drawCard);
        commands.put(JoinGameNetworkRequest.METHOD, this::joinGame);
        commands.put(ShowLobbyNetworkRequest.METHOD, this::showLobbies);
        commands.put(TotemNetworkRequest.METHOD, this::placeTotem);
        commands.put(SkipDrawNetworkRequest.METHOD, this::skipDraw);
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




    //Game methods
    private void createGame(NetworkRequest req, VirtualView view){
        Integer id = nextGameID.getAndIncrement();
        GameController gameController = null;

        //Flags used in finally block. Used to repeat less code in catch blocks and keep track of the advancement of this
        //process

        //Flags if the player has been put in the playerToGameMap. Used to rollback in case of subsequent errors
        boolean playerPut = false;

        //Flags if the game has been put in the games map. Used to rollback in case of subsequent errors
        boolean gamePut = false;

        //Flags the correct completion of the operations (no rollback required in this case)
        boolean success = false;

        try{
            NewGameNetworkRequest specReq = (NewGameNetworkRequest) req;

            GameResources gameResources = new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new
                    JsonOfferSupplier());
            Game gameInstance = new Game(specReq.getNumPlayers(), gameResources);
            gameController = new GameController(gameInstance, id);

            if(playerToGame.putIfAbsent(specReq.getPlayerID(), gameController) != null){
                view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(
                        new PlayerAlreadyInGameException(specReq.getPlayerID())));
                return;
            }
            playerPut = true;

            games.put(id, gameController);
            gamePut = true;

            NetworkObserver newPlayerObs = new NetworkObserver(view, specReq.getPlayerID());

            gameController.addPlayer(specReq.getPlayerID(), specReq.getColor(), newPlayerObs);
            //SOSEW
            System.out.println("Created game: " + specReq.getPlayerID());
            success = true;
        }catch(IOException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new BadNetworkRequestException("Unable to create game")));
        }catch(GameException e){

            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameInvariantException e){
            System.err.println("Game invariant violated: " + e.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally{
            if(!success){
                if(gamePut) games.remove(id);
                if(playerPut) playerToGame.remove(req.getPlayerID(), gameController);
            }
        }
    }


    private void drawCard(NetworkRequest req, VirtualView view){
        try{
            //No validity check required
            DrawNetworkRequest specReq = (DrawNetworkRequest) req;

            String requestorId = specReq.getPlayerID();
            GameController gameController = findGameFromPlayerUsername(requestorId);
            gameController.drawCard(requestorId, specReq.getCardID(), specReq.getBoardRows());


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


    private void joinGame(NetworkRequest req, VirtualView view) {

        JoinGameNetworkRequest joinReq = (JoinGameNetworkRequest) req;
        GameController gameToJoin = games.get(joinReq.getGameID());
        if (gameToJoin == null) {
            //takes the view back to lobby start
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(new LobbyNotFoundException(joinReq.getGameID())));
            return;
        }

        //putIfAbsent reservers the spot for the insert. If we don't to this, this can happen:
        // - check if a player is not in a game and get success, then the thread is preempted
        // - another request from the same nickname arrives and gets success
        // - now both request can register with the same nickname via playerToGame.put.
        // This happend because the first check and the second are not atomic operations. Using putIfAbsent makes
        // this operation atomic and saves us.
        // The prior insert is then correct if the insert of the player throws an error via playertogame.remove in catch
        // blocks.
        if (playerToGame.putIfAbsent(joinReq.getPlayerID(), gameToJoin) != null) {
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(
                    new PlayerAlreadyInGameException(joinReq.getPlayerID())));
            return;
        }

        try {

            gameToJoin.addPlayer(joinReq.getPlayerID(), joinReq.getColor(), new NetworkObserver(view, joinReq.getPlayerID()));
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
            controller.placeTotem(totemNetworkRequest.getPlayerID(), totemNetworkRequest.getOfferTrackID());

        }catch(NetworkException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameInvariantException e){
            System.err.println("Invariant violated: " + e.getMessage());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

    }

    private void skipDraw(NetworkRequest req, VirtualView view){
        try{
            SkipDrawNetworkRequest skipReq = (SkipDrawNetworkRequest) req;
            GameController controller = findGameFromPlayerUsername(req.getPlayerID());
            controller.skipDraw(skipReq.getPlayerID(), skipReq.getBoardRow());
        }catch(NetworkException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameException e){
            view.receiveErrorMessage(ErrorMessageFactory.createErrorMessage(e));
        }catch(GameInvariantException e){
            System.err.println("Invariant violated: " + e.getMessage());
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
