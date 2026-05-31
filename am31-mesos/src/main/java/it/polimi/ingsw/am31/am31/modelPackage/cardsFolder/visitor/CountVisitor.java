package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;

/** Counts how many cards of each type appear in a card collection. */
public class CountVisitor implements TribeVisitor{

    private int huntersCount;
    private int farmersCount;
    private int shamanCount;
    private int builderCount;
    private int artistsCount;
    private int inventorsCount;
    private int eventsCount;

    public CountVisitor() {
        huntersCount = 0;
        farmersCount =0;
        shamanCount = 0;
        builderCount = 0;
        artistsCount = 0;
        inventorsCount = 0;
        eventsCount = 0;
    }

    /**
     * Increase hunters counter
     * @param hunter the Hunter card being visited
     */
    @Override
    public void visit(Hunter hunter) {
        this.huntersCount++;
    }

    /**
     * Increase farmers counter
     * @param farmer the Farmer card being visited
     */
    @Override
    public void visit(Farmer farmer) {
        this.farmersCount++;
    }

    /**
     * Increase shamans counter
     * @param shaman the Shaman card being visited
     */
    @Override
    public void visit(Shaman shaman) {
        this.shamanCount++;
    }

    /**
     * Increase builders counter
     * @param builder the Builder card being visited
     */
    @Override
    public void visit(Builder builder) {
        this.builderCount++;
    }

    /**
     * Increase artists counter
     * @param artist the Artist card being visited
     */
    @Override
    public void visit(Artist artist) {
        this.artistsCount++;
    }

    /**
     * Increase inventors counter
     * @param inventor the Inventor card being visited
     */
    @Override
    public void visit(Inventor inventor) {
        this.inventorsCount++;
    }

    /**
     * Increase events counter
     * @param event the EventCard being visited
     */
    public void visit(EventCard event) {
        this.eventsCount++;
    }

    /**
     * Ignored
     */
    @Override
    public void visit(BuildingCard building){
    }

    /**
     * Increase events counter
     * @param huntEventCard the HuntEventCard being visited
     */
    @Override
    public void visit(HuntEventCard huntEventCard) {
        this.eventsCount++;
    }

    /**
     * Increase events counter
     * @param sustainEventCard the SustainEventCard being visited
     */
    @Override
    public void visit(SustainEventCard sustainEventCard) {
        this.eventsCount++;
    }

    /**
     * Increase events counter
     * @param ritualEventCard the RitualEventCard being visited
     */
    @Override
    public void visit(RitualEventCard ritualEventCard) {
        this.eventsCount++;
    }

    /**
     * Increase events counter
     * @param paintingEventCard the PaintingEventCard being visited
     */
    @Override
    public void visit(PaintingEventCard paintingEventCard) {
        this.eventsCount++;
    }
    
    /** @return the number of Hunter cards counted */
    public int getHunters(){return huntersCount; }

    /** @return the number of Farmer cards counted */
    public int getFarmers(){return farmersCount; }

    /** @return the number of Shaman cards counted */
    public int getShamans(){return shamanCount; }

    /** @return the number of Builder cards counted */
    public int getBuilders(){return builderCount; }

    /** @return the number of Inventor cards counted */
    public int getInventors(){return inventorsCount; }

    /** @return the number of Artist cards counted */
    public int getArtists(){return artistsCount;}

    /** @return the number of Event cards counted */
    public int getEvent(){return eventsCount;}
    


    /** @return the total number of character cards, excluding events and buildings. */
    public int getTotalCharacters(){
        return huntersCount + builderCount + shamanCount + farmersCount + inventorsCount + artistsCount;
    }

    /** Resets all counters to zero, allowing this visitor to be reused. */
    public void reset(){
        huntersCount = 0;
        farmersCount =0;
        shamanCount = 0;
        builderCount = 0;
        artistsCount = 0;
        inventorsCount = 0;
        eventsCount = 0;
    }

}
