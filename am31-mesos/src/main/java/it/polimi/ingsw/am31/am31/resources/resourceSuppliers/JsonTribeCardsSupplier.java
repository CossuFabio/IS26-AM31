package it.polimi.ingsw.am31.am31.resources.resourceSuppliers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.RitualEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.SustainEventCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.GameConstants;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.ResourcesPaths;
import it.polimi.ingsw.am31.am31.resources.IResourceSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonTribeCardsSupplier implements IResourceSupplier<List<Card>> {

    private final List<Card> resources;

    public JsonTribeCardsSupplier() throws IOException{
        ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        InputStream input = getClass().getResourceAsStream(ResourcesPaths.TRIBE_CATALOG_JSON_PATH);
        if (input == null) throw new IOException("TribeCard catalog not found");
        List<Card> loaded = mapper.readValue(input, new TypeReference<List<Card>>() {});

        //This is where the shuffling is made. This supplier injects an already shuffled deck
        Collections.shuffle(loaded);

        //Added at the end so they are in the final slots of the deck
        List<Card> finalCards = new ArrayList<Card>();

        finalCards.add(new SustainEventCard(GameConstants.FINAL_SUSTAIN_ID, GameConstants.FINAL_EVENTS_ERA,GameConstants.FINAL_SUSTAIN_MALUS));
        finalCards.add(new RitualEventCard( GameConstants.FINAL_RITUAL_ID, GameConstants.FINAL_EVENTS_ERA,GameConstants.FINAL_RITUAL_MALUS,GameConstants.FINAL_RITUAL_BONUS));

        Collections.shuffle(finalCards);

        loaded.addAll(finalCards);

        this.resources = loaded;
    }


    @Override
    public List<Card> getResources(){
        return resources.stream().toList();
    }

}