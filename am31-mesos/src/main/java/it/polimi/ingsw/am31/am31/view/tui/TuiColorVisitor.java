package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

public class TuiColorVisitor implements TribeVisitor {

    private boolean isBuilding = false;

    @Override
    public void visit(Hunter hunter) {  //Hunters
        //System.out.println(ansi().fgRed().a(cardId+" "+hunter).reset());
        //this way it only selects the color, and you decide what to write eachtime
        System.out.print(ansi().fgRed().toString());        isBuilding = false;

    }

    @Override
    public void visit(Shaman shaman) {  //Shaman
        System.out.print(ansi().fgCyan());        isBuilding = false;


    }

    @Override
    public void visit(Farmer farmer) { //Farmers
        System.out.print(ansi().fgGreen());        isBuilding = false;

    }
    @Override
    public void visit(Inventor inventor) { //Inventors
        System.out.print(ansi().fg(BLUE));        isBuilding = false;

    }

    @Override
    public void visit(Builder builder) { //Builders

        System.out.print(ansi().fg(Ansi.Color.MAGENTA));        isBuilding = false;

    }

    @Override
    public void visit(Artist artist) { //Artists
        System.out.print(ansi().fg(YELLOW));
        isBuilding = false;
    }

    @Override
    public void visit(BuildingCard card){ //Buildings with descriptions
        isBuilding = true;
        System.out.print(ansi().fg(Ansi.Color.CYAN));

    }
    public void visit(EventCard eventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }
    @Override
    public void visit(HuntEventCard huntEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    @Override
    public void visit(SustainEventCard sustainEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }


    @Override
    public void visit(RitualEventCard ritualEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    @Override
    public void visit(PaintingEventCard paintingEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    public boolean isBuilding (){return this.isBuilding;}

    public boolean checkBuilding (Card c){
        c.acceptVisit(this);
        return isBuilding;
    }
}
