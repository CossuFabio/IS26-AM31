package it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.JSONSuppliers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.EffectsCatalog;
import it.polimi.ingsw.am31.am31.modelPackage.modelUtilities.ResourcesPaths;
import it.polimi.ingsw.am31.am31.modelPackage.resourceSuppliers.IResourceSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonBuildingCardsSupplier implements IResourceSupplier<List<BuildingCard>> {


    private final List<BuildingCard> resources;

    private final EffectsCatalog effectsCatalog;



    public JsonBuildingCardsSupplier() throws IOException {
        effectsCatalog = new EffectsCatalog();

        ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
        InputStream input = getClass().getResourceAsStream(ResourcesPaths.BUILDINGCARD_CATALOG_JSON_PATH);
        if (input == null) throw new IOException("TribeCard catalog not found");

        this.resources =  new ArrayList<>();

        for(JsonNode node : mapper.readTree(input)){

            resources.add(new BuildingCard(
                    node.get("cardId").asText(),
                    node.get("era").asInt(),
                    node.get("cost").asInt(),
                    node.get("prestigePointsGained").asInt(),
                    effectsCatalog.getEffect(node.get("effectId").asText())
            ));
        }

    }

    @Override
    public List<BuildingCard> getResources(){
        return resources.stream().toList();
    }


}
