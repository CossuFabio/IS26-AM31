package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.gameInvariantException.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamesManager {

    private List<GameController> activeGames;

    public GamesManager() {
        activeGames = new ArrayList<GameController>();
    }

    //TODO: method for creating new game, calls for GameController constructor
    public void createGame(int nplayers) throws IOException {
        GameResources gameResources = new GameResources(new JsonTribeCardsSupplier(), new JsonBuildingCardsSupplier(), new JsonOfferSupplier());
        Game gameinstance = new Game (nplayers, gameResources);
        activeGames.add(new GameController(gameinstance));
    }

    public GameController getGameControllerWithPlayer(String nickname) throws PlayerNotFoundException {
            for (GameController g : activeGames) {
                if(g.isPlayerInGame(nickname))
                    return g;
            }
            throw new PlayerNotFoundException(nickname);
    }

    //returns immutable lists
    public List<GameController> getActiveGames(){return this.activeGames.stream().toList();}

    public GameController getControllerI(int i) {
        return this.activeGames.get(i);
    }

    //TODO: method for deleting active game
}
