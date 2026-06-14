package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonLocalOfferSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonTribeCardsSupplier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Translates resource IDs into actual game objects (Cards, LocalOfferCards) for the local client state.
 * This class loads all available card and offer card templates at initialization.
 */
public class LocalResourceTranslator {

    /**
     * A map storing all available Card objects, indexed by their card ID.
     */
    private final Map<String, Card> cardsById;
    /**
     * A map storing all available LocalOfferCard templates, indexed by their offer card ID.
     */
    private final Map<String, LocalOfferCard> offerTemplatesById;

    /**
     * Constructs a new LocalResourceTranslator.
     * It loads all tribe cards, building cards, and local offer card templates from JSON resources.
     * @throws IOException If an I/O error occurs during resource loading.
     */
    public LocalResourceTranslator() throws IOException {
        this.cardsById = new HashMap<>();
        new JsonTribeCardsSupplier()
                .getResources()
                .forEach(card -> cardsById.put(card.getCardId(), card));

        new JsonBuildingCardsSupplier()
                .getResources()
                .forEach(card -> cardsById.put(card.getCardId(), card));

        this.offerTemplatesById = new JsonLocalOfferSupplier().getResources().stream()
                .collect(Collectors.toUnmodifiableMap(
                        LocalOfferCard::getOfferCardId, c -> c));
    }

    /**
     * Retrieves a Card object by its unique ID.
     * @param id The unique ID of the card to retrieve.
     * @return The Card object corresponding to the given ID.
     * @throws IllegalStateException If no card is found with the specified ID.
     */
    public Card retrieveCard(String id) throws IllegalStateException {
        Card card = cardsById.get(id);
        if(card == null) throw new IllegalStateException("Card not found: " + id);
        return card;
    }

    /**
     * Creates a LocalOfferCard instance based on a template and specific game state.
     * If the card is free, it returns the template directly. Otherwise, it creates a new
     * LocalOfferCard with the provided player nickname.
     * @param id The unique ID of the offer card template.
     * @param isFree True if the offer card is free (unclaimed), false otherwise.
     * @param totemPlayerNickname The nickname of the player who claimed the offer card, if not free.
     * @return A LocalOfferCard instance.
     * @throws IllegalStateException If no offer card template is found with the specified ID.
     */
    public LocalOfferCard createOfferCard(String id, boolean isFree, String totemPlayerNickname) throws IllegalStateException {

        LocalOfferCard template = offerTemplatesById.get(id);
        if (template == null) throw new IllegalStateException("OfferCard not found: " + id);

        if (isFree) return template;

        return new LocalOfferCard(
                template.getOfferCardId(),
                totemPlayerNickname,
                false,
                template.getFood(),
                template.getDrawFromUpper(),
                template.getDrawFromUnder()
        );
    }
}