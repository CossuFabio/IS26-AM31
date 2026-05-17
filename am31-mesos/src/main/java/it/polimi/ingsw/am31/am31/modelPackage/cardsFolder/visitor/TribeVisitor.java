package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;

public interface TribeVisitor {

    void visit (Hunter hunter);
    void visit (Shaman shaman);
    void visit (Farmer farmer);
    void visit (Inventor inventor);
    void visit (Builder builder);
    void visit (Artist artist);
    void visit (BuildingCard building);
    void visit (HuntEventCard huntEventCard);
    void visit (SustainEventCard sustainEventCard);
    void visit (RitualEventCard ritualEventCard);
    void visit (PaintingEventCard paintingEventCard);
}
