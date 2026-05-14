package it.polimi.ingsw.am31.am31.testUtils;

import it.polimi.ingsw.am31.am31.modelPackage.Game;
import it.polimi.ingsw.am31.am31.resources.GameResources;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonTribeCardsSupplier;
import it.polimi.ingsw.am31.am31.testUtils.board.OfferCardFluentBuilder;
import it.polimi.ingsw.am31.am31.testUtils.player.PlayerFluentBuilder;

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




}
