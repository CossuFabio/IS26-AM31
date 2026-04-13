package it.polimi.ingsw.am31.am31.network;

import it.polimi.ingsw.am31.am31.controller.GameController;
import it.polimi.ingsw.am31.am31.exceptions.PlayerNotFoundException;
import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;

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
        GameResources gameResources = new GameResources(); //fede plz come si crea un game
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

    public void showActiveGames () {
        int i=1;
        for (GameController g : activeGames) {
            System.out.println("Game" + i + g);
            i++;
        }
    }

    public void joinGame (String nickname, int i) {

    }

    public GameController getControllerI(int i) {
        return this.activeGames.get(i);
    }
    //TODO: method for deleting active game
}
