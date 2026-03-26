package it.polimi.ingsw.am31.am31.modelPackage.modelUtilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CardLoader {
    private final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);

    //method to load the cards of the Tribe Deck
    public List<Card> tribeCardLoader () throws IOException {

        InputStream input = getClass().getResourceAsStream("/TribeCard.json");
        if(input==null)
            throw new IOException("File mazzo non trovato");
        return mapper.readValue(input, new TypeReference<List<Card>>() {});
    }

    //method to load the cards of the OfferTrack
    public List<OfferCard> offerCardLoader() throws IOException {
        InputStream input = getClass().getResourceAsStream("/OfferCard.json");
        if(input==null)
            throw new IOException("File offer non trovato");
        return mapper.readValue(input, new TypeReference<List<OfferCard>>() {});
    }
}
