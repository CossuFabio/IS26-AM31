package it.polimi.ingsw.am31.am31;

import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.GameResources;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonOfferSupplier;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers.JsonTribeCardsSupplier;

public class TestUtilities {

    //Make tests easier to write for json based tests
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
}
