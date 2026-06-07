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

    /**
     * Sets the terminal color for Hunter cards to Red and updates the building status.
     * @param hunter The hunter card being visited.
     */
    @Override
    public void visit(Hunter hunter) {  //Hunters
        //System.out.println(ansi().fgRed().a(cardId+" "+hunter).reset());
        //this way it only selects the color, and you decide what to write eachtime
        System.out.print(ansi().fgRed().toString());        isBuilding = false;
    }

    /**
     * Sets the terminal color for Shaman cards to Cyan and updates the building status.
     * @param shaman The shaman card being visited.
     */
    @Override
    public void visit(Shaman shaman) {  //Shaman
        System.out.print(ansi().fgCyan());        isBuilding = false;
    }

    /**
     * Sets the terminal color for Farmer cards to Green and updates the building status.
     * @param farmer The farmer card being visited.
     */
    @Override
    public void visit(Farmer farmer) { //Farmers
        System.out.print(ansi().fgGreen());        isBuilding = false;
    }

    /**
     * Sets the terminal color for Inventor cards to Blue and updates the building status.
     * @param inventor The inventor card being visited.
     */
    @Override
    public void visit(Inventor inventor) { //Inventors
        System.out.print(ansi().fg(BLUE));        isBuilding = false;
    }

    /**
     * Sets the terminal color for Builder cards to Magenta and updates the building status.
     * @param builder The builder card being visited.
     */
    @Override
    public void visit(Builder builder) { //Builders
        System.out.print(ansi().fg(Ansi.Color.MAGENTA));        isBuilding = false;
    }

    /**
     * Sets the terminal color for Artist cards to Yellow and updates the building status.
     * @param artist The artist card being visited.
     */
    @Override
    public void visit(Artist artist) { //Artists
        System.out.print(ansi().fg(YELLOW));
        isBuilding = false;
    }

    /**
     * Sets the terminal color for Building cards to Cyan and updates the building status to true.
     * @param card The building card being visited.
     */
    @Override
    public void visit(BuildingCard card){ //Buildings with descriptions
        isBuilding = true;
        System.out.print(ansi().fg(Ansi.Color.CYAN));
    }

    /**
     * Sets the terminal color for generic Event cards to Bright White and updates the building status.
     * @param eventCard The event card being visited.
     */
    public void visit(EventCard eventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    /**
     * Sets the terminal color for Hunt Event cards to Bright White and updates the building status.
     * @param huntEventCard The hunt event card being visited.
     */
    @Override
    public void visit(HuntEventCard huntEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    /**
     * Sets the terminal color for Sustain Event cards to Bright White and updates the building status.
     * @param sustainEventCard The sustain event card being visited.
     */
    @Override
    public void visit(SustainEventCard sustainEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    /**
     * Sets the terminal color for Ritual Event cards to Bright White and updates the building status.
     * @param ritualEventCard The ritual event card being visited.
     */
    @Override
    public void visit(RitualEventCard ritualEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    /**
     * Sets the terminal color for Painting Event cards to Bright White and updates the building status.
     * @param paintingEventCard The painting event card being visited.
     */
    @Override
    public void visit(PaintingEventCard paintingEventCard) {
        System.out.print(ansi().fgBright(Ansi.Color.WHITE));        isBuilding = false;
    }

    /**
     * Returns whether the last visited card was a building card.
     * @return true if the last visited card was a BuildingCard, false otherwise.
     */
    public boolean isBuilding (){return this.isBuilding;}

    /**
     * Triggers the visit on a specific card to determine if it is a building card.
     * @param c The card to check.
     * @return true if the card is a building card, false otherwise.
     */
    public boolean checkBuilding (Card c){
        c.acceptVisit(this);
        return isBuilding;
    }
}
