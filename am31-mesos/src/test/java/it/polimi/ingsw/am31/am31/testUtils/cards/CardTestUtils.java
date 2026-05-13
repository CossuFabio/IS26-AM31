package it.polimi.ingsw.am31.am31.testUtils.cards;

import it.polimi.ingsw.am31.am31.testUtils.cards.buildingBuilders.BuildingFluentBuilder;
import it.polimi.ingsw.am31.am31.testUtils.cards.characterBuilders.*;
import it.polimi.ingsw.am31.am31.testUtils.cards.eventBuilders.*;


public class CardTestUtils {

    /*------------Characters------------*/

    public static BuilderFluentBuilder createBuilder(){
        return new BuilderFluentBuilder();
    }

    public static ShamanFluentBuilder createShaman(){
        return new ShamanFluentBuilder();
    }

    public static FarmerFluentBuilder createFarmer(){
        return new FarmerFluentBuilder();
    }

    public static InventorFluentBuilder createInventor(){
        return new InventorFluentBuilder();
    }

    public static HunterFluentBuilder createHunter(){
        return new HunterFluentBuilder();
    }

    public static ArtistFluentBuilder createArtist(){
        return new ArtistFluentBuilder();
    }

    /*------------Events------------*/

    public static HuntEventFluentBuilder createHuntEvent(){
        return new HuntEventFluentBuilder();
    }

    public static PaintingEventFluentBuilder createPaintingEvent(){
        return new PaintingEventFluentBuilder();
    }

    public static RitualEventFluentBuilder createRitualEvent(){
        return new RitualEventFluentBuilder();
    }

    public static SustainEventFluentBuilder createSustainEvent(){
        return new SustainEventFluentBuilder();
    }

    /*------------Building------------*/
    public static BuildingFluentBuilder createBuilding(){
        return new BuildingFluentBuilder();
    }
}
