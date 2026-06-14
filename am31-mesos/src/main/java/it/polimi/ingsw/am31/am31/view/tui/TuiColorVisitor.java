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
    private String colorString = ansi().reset().toString();

    /**
     * Returns the ANSI color string set by the last visit call.
     */
    public String getColorString() { return colorString; }

    @Override
    public void visit(Hunter hunter) {
        colorString = ansi().fgRed().toString();
        isBuilding = false;
    }

    @Override
    public void visit(Shaman shaman) {
        colorString = ansi().fgCyan().toString();
        isBuilding = false;
    }

    @Override
    public void visit(Farmer farmer) {
        colorString = ansi().fgGreen().toString();
        isBuilding = false;
    }

    @Override
    public void visit(Inventor inventor) {
        colorString = ansi().fg(BLUE).toString();
        isBuilding = false;
    }

    @Override
    public void visit(Builder builder) {
        colorString = ansi().fg(Ansi.Color.MAGENTA).toString();
        isBuilding = false;
    }

    @Override
    public void visit(Artist artist) {
        colorString = ansi().fg(YELLOW).toString();
        isBuilding = false;
    }

    @Override
    public void visit(BuildingCard card) {
        colorString = ansi().fg(Ansi.Color.CYAN).toString();
        isBuilding = true;
    }

    public void visit(EventCard eventCard) {
        colorString = ansi().fgBright(Ansi.Color.WHITE).toString();
        isBuilding = false;
    }

    @Override
    public void visit(HuntEventCard huntEventCard) {
        colorString = ansi().fgBright(Ansi.Color.WHITE).toString();
        isBuilding = false;
    }

    @Override
    public void visit(SustainEventCard sustainEventCard) {
        colorString = ansi().fgBright(Ansi.Color.WHITE).toString();
        isBuilding = false;
    }

    @Override
    public void visit(RitualEventCard ritualEventCard) {
        colorString = ansi().fgBright(Ansi.Color.WHITE).toString();
        isBuilding = false;
    }

    @Override
    public void visit(PaintingEventCard paintingEventCard) {
        colorString = ansi().fgBright(Ansi.Color.WHITE).toString();
        isBuilding = false;
    }

    /**
     * Returns whether the last visited card was a building card.
     */
    public boolean isBuilding() { return this.isBuilding; }

    /**
     * Triggers the visit on a specific card to determine if it is a building card.
     */
    public boolean checkBuilding(Card c) {
        c.acceptVisit(this);
        return isBuilding;
    }
}
