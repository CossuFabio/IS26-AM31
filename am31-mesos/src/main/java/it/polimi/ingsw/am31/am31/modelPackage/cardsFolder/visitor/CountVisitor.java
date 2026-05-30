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


    public void visit(Hunter hunter) {
        this.huntersCount++;
    }


    public void visit(Farmer farmer) {
        this.farmersCount++;
    }

    public void visit(Shaman shaman) {
        this.shamanCount++;
    }

    public void visit(Builder builder) {
        this.builderCount++;
    }


    public void visit(Artist artist) {
        this.artistsCount++;
    }


    public void visit(Inventor inventor) {
        this.inventorsCount++;
    }

    public void visit(EventCard event) {
        this.eventsCount++;
    }
@Override
    public void visit(BuildingCard building){
    }

    @Override
    public void visit(HuntEventCard huntEventCard) {
        this.eventsCount++;
    }

    @Override
    public void visit(SustainEventCard sustainEventCard) {
        this.eventsCount++;
    }

    @Override
    public void visit(RitualEventCard ritualEventCard) {
        this.eventsCount++;
    }

    @Override
    public void visit(PaintingEventCard paintingEventCard) {
        this.eventsCount++;
    }

    public int getHunters(){return huntersCount; }
    public int getFarmers(){return farmersCount; }
    public int getShamans(){return shamanCount; }
    public int getBuilders(){return builderCount; }
    public int getInventors(){return inventorsCount; }
    public int getArtists(){return artistsCount;}
    public int getEvent(){return eventsCount;}


    /** Returns the total number of character cards, excluding events and buildings. */
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
