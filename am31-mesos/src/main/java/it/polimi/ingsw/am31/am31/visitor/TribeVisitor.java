package it.polimi.ingsw.am31.am31.visitor;
import it.polimi.ingsw.am31.am31.cards.*;

public interface TribeVisitor {

    void visit (Hunter hunter);
    void visit (Shaman shaman);
    void visit (Farmer farmer);
    void visit (SustainEventCard event);
    void visit (Inventor inventor);
    void visit (Builder builder);
    void visit (Artist artist);

}
