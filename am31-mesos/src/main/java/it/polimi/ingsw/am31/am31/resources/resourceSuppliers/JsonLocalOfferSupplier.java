package it.polimi.ingsw.am31.am31.resources.resourceSuppliers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.ResourcesPaths;
import it.polimi.ingsw.am31.am31.resources.IResourceSupplier;
import it.polimi.ingsw.am31.am31.view.localState.LocalOfferCard;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonLocalOfferSupplier implements IResourceSupplier<List<LocalOfferCard>> {

    private final List<LocalOfferCard> resources;

    public JsonLocalOfferSupplier() throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        InputStream input = getClass().getResourceAsStream(ResourcesPaths.OFFERCARD_CATALOG_JSON_PATH);
        if (input == null) throw new IOException("OfferCard catalog not found!");
        this.resources = new ArrayList<>();

        for (JsonNode node : mapper.readTree(input)) {
            resources.add(
                    new LocalOfferCard(
                            node.get("offerCardId").asText(),
                            null,
                            true,
                            node.get("food").asInt(),
                            node.get("drawFromUpper").asInt(),
                            node.get("drawFromUnder").asInt()
                    )
            );
        }
    }

    @Override
    public List<LocalOfferCard> getResources() {
        return resources.stream().toList();
    }
}