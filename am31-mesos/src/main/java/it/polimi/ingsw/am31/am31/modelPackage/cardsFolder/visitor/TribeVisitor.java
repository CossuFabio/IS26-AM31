package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;

/**
 * Visitor interface that implements the visitor pattern for all classes in the card hierarchy.
 * Each overload dispatches to the correct card type.
 */
public interface TribeVisitor {

    /** @param hunter the Hunter card being visited */
    void visit(Hunter hunter);

    /** @param shaman the Shaman card being visited */
    void visit(Shaman shaman);

    /** @param farmer the Farmer card being visited */
    void visit(Farmer farmer);

    /** @param inventor the Inventor card being visited */
    void visit(Inventor inventor);

    /** @param builder the Builder card being visited */
    void visit(Builder builder);

    /** @param artist the Artist card being visited */
    void visit(Artist artist);

    /** @param building the BuildingCard being visited */
    void visit(BuildingCard building);

    /** @param huntEventCard the HuntEventCard being visited */
    void visit(HuntEventCard huntEventCard);

    /** @param sustainEventCard the SustainEventCard being visited */
    void visit(SustainEventCard sustainEventCard);

    /** @param ritualEventCard the RitualEventCard being visited */
    void visit(RitualEventCard ritualEventCard);

    /** @param paintingEventCard the PaintingEventCard being visited */
    void visit(PaintingEventCard paintingEventCard);
}
