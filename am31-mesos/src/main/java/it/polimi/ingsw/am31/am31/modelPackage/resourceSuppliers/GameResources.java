package it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;

import java.io.IOException;
import java.util.List;

public class GameResources {

    private final IResourceSupplier<List<Card>> tribeCardsSupplier;
    private final IResourceSupplier<List<BuildingCard>> buildingsSupplier;
    private final IResourceSupplier<List<OfferCard>> offerCardsSupplier;

    public GameResources(IResourceSupplier<List<Card>> tribeCardsSupplier,
                         IResourceSupplier<List<BuildingCard>> buildingsSupplier,
                         IResourceSupplier<List<OfferCard>> offerCardsSupplier){
        this.tribeCardsSupplier = tribeCardsSupplier;
        this.buildingsSupplier = buildingsSupplier;
        this.offerCardsSupplier = offerCardsSupplier;
    }

    public List<Card> getTribeCards() throws IOException {
        return tribeCardsSupplier.getResources();
    }

    public List<BuildingCard> getBuildingCards() throws IOException {
        return buildingsSupplier.getResources();
    }

    public List<OfferCard> getOfferCards() throws IOException {
        return offerCardsSupplier.getResources();
    }
}