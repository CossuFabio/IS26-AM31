package it.polimi.ingsw.am31.am31;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.cards.Card;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CardLoader {
    private final ObjectMapper mapper = new ObjectMapper();

    //method to load the cards of the Tribe Deck
    public List<Card> tribeCardLoader () throws IOException {
        InputStream input = getClass().getResourceAsStream("/TribeCard.json");
        return mapper.readValue(input, new TypeReference<List<Card>>() {});
    }

    //method to load the cards of the OfferTrack
    public List<OfferCard> offerCardLoader() throws IOException {
        InputStream input = getClass().getResourceAsStream("/OfferCard.json");
        return mapper.readValue(input, new TypeReference<List<OfferCard>>() {});
    }
}
