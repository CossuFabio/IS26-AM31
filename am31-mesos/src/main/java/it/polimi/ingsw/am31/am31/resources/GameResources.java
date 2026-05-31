package it.polimi.ingsw.am31.am31.resources;

import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;

import java.util.List;

/**
 * Holds the resource suppliers for a game of Mesos, providing dependency injection
 * of character/event cards, building cards, and offer cards.
 */
public class GameResources {

    private final IResourceSupplier<List<Card>> tribeCardsSupplier;
    private final IResourceSupplier<List<BuildingCard>> buildingsSupplier;
    private final IResourceSupplier<List<OfferCard>> offerCardsSupplier;

    /**
     * @param tribeCardsSupplier  supplier for character and event cards
     * @param buildingsSupplier   supplier for building cards
     * @param offerCardsSupplier  supplier for offer cards
     */
    public GameResources(IResourceSupplier<List<Card>> tribeCardsSupplier,
                         IResourceSupplier<List<BuildingCard>> buildingsSupplier,
                         IResourceSupplier<List<OfferCard>> offerCardsSupplier){
        this.tribeCardsSupplier = tribeCardsSupplier;
        this.buildingsSupplier = buildingsSupplier;
        this.offerCardsSupplier = offerCardsSupplier;
    }

    /** @return the list of character and event cards for this game */
    public List<Card> getTribeCards(){
        return tribeCardsSupplier.getResources();
    }

    /** @return the list of building cards for this game */
    public List<BuildingCard> getBuildingCards(){
        return buildingsSupplier.getResources();
    }

    /** @return the list of offer cards for this game */
    public List<OfferCard> getOfferCards(){
        return offerCardsSupplier.getResources();
    }
}