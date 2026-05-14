package it.polimi.ingsw.am31.am31.resources.resourceSuppliers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.boardFolder.OfferCard;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.ResourcesPaths;
import it.polimi.ingsw.am31.am31.resources.IResourceSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonOfferSupplier implements IResourceSupplier<List<OfferCard>> {

    private final List<OfferCard> resources;


    public JsonOfferSupplier() throws IOException{

        ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        InputStream input = getClass().getResourceAsStream(ResourcesPaths.OFFERCARD_CATALOG_JSON_PATH);
        if (input == null) throw new IOException("OfferCard Catalog not found! ");

        this.resources = mapper.readValue(input, new TypeReference<List<OfferCard>>() {});
    }

    @Override
    public List<OfferCard> getResources(){
        return resources.stream().toList();
    }
}