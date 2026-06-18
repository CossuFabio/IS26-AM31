package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import org.apache.commons.lang3.StringUtils;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.*;

/**
 * Prints the details inside a card, for the tui
 */
public class TuiCardPrintVisitor implements TribeVisitor
{
    private int layer = 1;
    private String description = null;

    /**
     * increases the layer value by 1.
     */
    public void nextLayer(){this.layer++;}

    public String getDescription() { return description; }

    //prints the details inside a card, for the tui

    @Override
    /**
     * Visit a Hunter card prints the current layer with its info in their formatting
     */
    public void visit(Hunter c) {
        switch (layer) {
            case 1:
                if (c.getMark())
                    print(c, centeredInsideBorder(" "+c.getCardId()+" "+"HUNTER [M] ", CARD_SIZE));
                else print(c, centeredInsideBorder(" "+c.getCardId()+" "+"HUNTER ", CARD_SIZE));
                 break;
            case 2: print(c, centeredInsideBorder("", CARD_SIZE));
                 break;
            case 3: print(c, centeredInsideBorder("", CARD_SIZE));
                 break;
            case 4: print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));
                 break;
        }
    }

    @Override
    /**
     * Visit a Shaman card prints the current layer with its info in their formatting
     */
    public void visit(Shaman c) {
        switch(layer){
            case 1: print(c, centeredInsideBorder(" "+c.getCardId()+" "+"SHAMAN ",CARD_SIZE));
                break;

            case 2:
                switch (c.getStars()) { //hardcoded correction because of visual issue
                    default:print(c, centeredInsideBorder(StringUtils.repeat("[*]", c.getStars()), CARD_SIZE));break;
                }break;

            case 3:print(c, centeredInsideBorder("",CARD_SIZE));break;

            case 4:print(c, centeredInsideBorder("era "+c.getEra()+"  "+c.getMinPlayers()+"+",CARD_SIZE));break;
        }
    }

    @Override
    /**
     * Visit a Farmer card prints the current layer with its info in their formatting
     */
    public void visit(Farmer c) {
        switch(layer) {
            case 1:print(c, centeredInsideBorder(" "+c.getCardId()+" "+"FARMER  ", CARD_SIZE));break;

            case 2:print(c, centeredInsideBorder("-" + c.getSustainDiscount() + "[F]      ", CARD_SIZE));break;

            case 3:print(c, centeredInsideBorder("", CARD_SIZE));break;

            case 4: print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    @Override
    /**
     * Visit a HuntEvent card prints the current layer with its info in their formatting
     */
    public void visit(HuntEventCard c) {
        switch(layer) {
            case 1: print(c, centeredInsideBorder("HUNT EVENT", CARD_SIZE));break;

            case 2:print(c, centeredInsideBorder(c.getFoodBonus() + "[F]+" + c.getPrestigePointsBonus() + " x hunter", CARD_SIZE));break;

            case 3:print(c, centeredInsideBorder("", CARD_SIZE));break;

            case 4:print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    @Override
    /**
     * Visit a SustainEvent card prints the current layer with its info in their formatting
     */
    public void visit(SustainEventCard c) {
        switch(layer) {
            case 1:print(c, centeredInsideBorder("SUSTAIN EVENT", CARD_SIZE));break;

            case 2:print(c, centeredInsideBorder("-1[F]/" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 3:    print(c, centeredInsideBorder("  x charCard", CARD_SIZE));break;

            case 4:    print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    /**
     * Visit a PaintingEvent card prints the current layer with its info in their formatting
     */
    @Override
    public void visit(PaintingEventCard c) {
        switch(layer) {
            case 1:print(c, centeredInsideBorder("PAINTING EVENT", CARD_SIZE));break;

            case 2: print(c, centeredInsideBorder(c.getMinArtist() + ": -" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 3:print(c, centeredInsideBorder(c.getMinArtist() + 1 + "+: " + c.getPrestigePointsBonus() + "x artist", CARD_SIZE));break;

            case 4:  print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    @Override
    /**
     * Visit a RitualEvent card prints the current layer with its info in their formatting
     */
    public void visit(RitualEventCard c) {
        switch(layer) {
            case 1:  print(c, centeredInsideBorder("SHAMAN RITUAL", CARD_SIZE));break;

            case 2:print(c, centeredInsideBorder("[*]>:  " + c.getPrestigePointsBonus(), CARD_SIZE));break;

            case 3: print(c, centeredInsideBorder("[*]<: -" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 4:  print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(EventCard c){
    }

    @Override
    /**
     * Visit a Inventor card prints the current layer with its info in their formatting
     */
    public void visit(Inventor c) {
        switch(layer) {
            case 1:print(c, centeredInsideBorder(" "+c.getCardId()+" "+"INVENTOR ", CARD_SIZE)); break;

            case 2: print(c, centeredInsideBorder("", CARD_SIZE));break;

            case 3:print(c, centeredInsideBorder(c.getIcon().toString(), CARD_SIZE));break;

            case 4: print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    @Override
    /**
     * Visit a Builder card prints the current layer with its info in their formatting
     */
    public void visit(Builder c) {
        switch(layer) {
            case 1:  print(c, centeredInsideBorder(" "+c.getCardId()+" "+"BUILDER ", CARD_SIZE));break;

            case 2:   print(c, centeredInsideBorder("    -" + c.getBuildingDiscount() + "[F]", CARD_SIZE));break;

            case 3:   print(c, centeredInsideBorder(c.getPrestigePoints() + "[PP]     ", CARD_SIZE));break;

            case 4: print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;

        }
    }

    @Override
    /**
     * Visit a Aritst card prints the current layer with its info in their formatting
     */
    public void visit(Artist c) {
        switch(layer) {
            case 1: print(c, centeredInsideBorder(" "+c.getCardId()+" "+"ARTIST ", CARD_SIZE));break;

            case 2: print(c, centeredInsideBorder("", CARD_SIZE));break;

            case 3: print(c, centeredInsideBorder("", CARD_SIZE));break;

            case 4:  print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    @Override
    /**
     * Visiting a building card prints the layers in black in a blue bg, and their description, on 2 lines if too long
     */
    public void visit(BuildingCard c) {
        this.description = c.getDescription();
        switch(layer) {
            case 1:  print(c, centeredInsideBorder(" "+c.getCardId()+" "+"BUILDING ", CARD_SIZE));break;
            case 2:  print(c, centeredInsideBorder("Cost:"+c.getCost()+"[F]", CARD_SIZE));break;
            case 3:  print(c, centeredInsideBorder("Gives:"+c.getPrestigePointsGained()+"[PP]", CARD_SIZE));break;
            case 4:  print(c, centeredInsideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
            default:break;
        }
    }
}