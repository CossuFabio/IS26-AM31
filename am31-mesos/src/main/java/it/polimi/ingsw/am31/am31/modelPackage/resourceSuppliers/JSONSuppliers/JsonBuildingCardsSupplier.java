package it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.ResourcesPaths;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.IResourceSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonBuildingCardsSupplier implements IResourceSupplier {


    private final List<Card> resources;

    public JsonBuildingCardsSupplier() throws IOException {

        ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        InputStream input = getClass().getResourceAsStream(ResourcesPaths.BUILDINGCARD_CATALOG_JSON_PATH);
        if (input == null) throw new IOException("TribeCard catalog not found");
        this.resources =  mapper.readValue(input, new TypeReference<List<Card>>() {});

    }

    @Override
    public List<Card> getResources() throws IOException {
        return resources;
    }


}
