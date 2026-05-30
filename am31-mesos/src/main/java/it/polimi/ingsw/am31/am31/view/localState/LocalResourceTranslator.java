package it.polimi.ingsw.am31.am31.view.localState;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonBuildingCardsSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonLocalOfferSupplier;
import it.polimi.ingsw.am31.am31.resources.resourceSuppliers.JsonTribeCardsSupplier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class LocalResourceTranslator {

    private final Map<String, Card> cardsById;
    private final Map<String, LocalOfferCard> offerTemplatesById;

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


    public Card retrieveCard(String id) throws IllegalStateException {
        Card card = cardsById.get(id);
        if(card == null) throw new IllegalStateException("Card not found: " + id);
        return card;
    }

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