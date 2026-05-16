package it.polimi.ingsw.am31.am31.testUtils;

import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Color;
import it.polimi.ingsw.am31.am31.modelPackage.playerFolder.Player;
import it.polimi.ingsw.am31.am31.resources.GameResources;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.testUtils.board.OfferCardFluentBuilder;
import it.polimi.ingsw.am31.am31.testUtils.player.PlayerFluentBuilder;

import java.util.ArrayList;
import java.util.List;

public class TestUtilities {

    //Make tests easier to write for JSON based tests
    public static GameResources getJSONGameResources() {
        try {
            return new GameResources(
                    new JsonTribeCardsSupplier(),
                    new JsonBuildingCardsSupplier(),
                    new JsonOfferSupplier()
            );
        } catch (Exception e) {
            throw new RuntimeException("Errore nel caricamento dei JSON nei test", e);
        }
    }

    public static Game createGame(int nPlayers) {
        try {
            return new Game(nPlayers, getJSONGameResources());
        } catch (Exception e) {
            throw new RuntimeException("Errore nella creazione del Game di test", e);
        }
    }

    public static PlayerFluentBuilder createPlayer(){
        return new PlayerFluentBuilder();
    }

    public static OfferCardFluentBuilder createOfferCard(){
        return new OfferCardFluentBuilder();
    }

    public static List<Player> createLobby(int nPlayers){

        List<Player> players = new ArrayList<>();
        Color[] colors = Color.values();
        for(int i = 0; i<nPlayers; i++){
            players.add(
                    createPlayer()
                            .name("Player"+i)
                            .color(colors[i % colors.length]) //Allows to create lobby with >5 players
                            .build()
            );
        }
        return players;
    }


}
