package it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.ResourcesPaths;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.IResourceSupplier;

import java.io.IOException;
import java.io.InputStream;
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

        this.resources = loaded;
    }


    @Override
    public List<Card> getResources(){
        return resources.stream().toList();
    }

}