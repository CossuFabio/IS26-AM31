package it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;

public interface TribeVisitor {

    void visit (Hunter hunter);
    void visit (Shaman shaman);
    void visit (Farmer farmer);
    void visit (EventCard event);
    void visit (Inventor inventor);
    void visit (Builder builder);
    void visit (Artist artist);
    void visit (BuildingCard building);
}
