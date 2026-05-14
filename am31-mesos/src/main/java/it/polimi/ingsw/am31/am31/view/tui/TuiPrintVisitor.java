package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.EventCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.BLUE;
import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

public class TuiPrintVisitor implements TribeVisitor {

    private boolean isBuilding = false;

    @Override
    public void visit(Hunter hunter) {  //Hunters
        //System.out.println(ansi().fgRed().a(cardId+" "+hunter).reset());
        //this way it only selects the color, and you decide what to write eachtime
        System.out.print(ansi().fgRed().toString());
    }

    @Override
    public void visit(Shaman shaman) {  //Shaman
        System.out.print(ansi().fgCyan());

    }

    @Override
    public void visit(Farmer farmer) { //Farmers
        System.out.print(ansi().fgGreen());
    }

    @Override
    public void visit(EventCard event) { //Events
        System.out.print(ansi().bgBright(Ansi.Color.WHITE).fgBlack());}

    @Override
    public void visit(Inventor inventor) { //Inventors
        System.out.print(ansi().fg(BLUE));
    }

    @Override
    public void visit(Builder builder) { //Builders

        System.out.print(ansi().fg(Ansi.Color.MAGENTA));
    }

    @Override
    public void visit(Artist artist) { //Artists
        System.out.println(ansi().fg(YELLOW));
    }

    @Override
    public void visit(BuildingCard card){ //Buildings with descriptions
        isBuilding = true;
        System.out.print(ansi().bg(Ansi.Color.BLUE).fg(Ansi.Color.BLACK));

    }
    public boolean isBuilding (){return this.isBuilding;}
}
