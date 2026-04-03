package it.polimi.ingsw.am31.am31.controller;

import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;

import java.io.IOException;

public class GameController {
    private ResourceFinder resourceFinder;
    private Game gameInstance;

    //creates new game with server resources
    public void createGameInstance(int nPlayers, GameResources gameResources) throws IOException {
        this.gameInstance = new Game(nPlayers, gameResources);
    }

    //TODO: Implement this, determine messages player needs to send to join
    public void addPlayerToGame(){

    }


}
