package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.events.Event;

import static it.polimi.ingsw.am31.am31.view.tui.TUIConfig.*;

public class TuiCardPrintVisitor implements TribeVisitor
{
    private int layer = 1;

    public void nextLayer(){this.layer++;}

    //prints the detailed inside of a card, for the tui

    @Override
    public void visit(Hunter c) {
        switch (layer) {
            case 1:
                if (c.getMark())
                    print(c, printMiddle("HUNTER   ♦", CARD_SIZE));
                else print(c, printMiddle("HUNTER    ", CARD_SIZE));
                 break;
            case 2: print(c, printMiddle("", CARD_SIZE));
                 break;
            case 3: print(c, printMiddle("", CARD_SIZE));
                 break;
            case 4: print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));
                 break;
        }
    }

    @Override
    public void visit(Shaman c) {
        switch(layer){
            case 1: print(c,printMiddle("SHAMAN   ",CARD_SIZE));break;

            case 2: print(c,printMiddle(StringUtils.repeat("★",c.getStars()),CARD_SIZE));break;

            case 3:print(c,printMiddle("",CARD_SIZE));break;

            case 4:print(c,printMiddle("era "+c.getEra()+"  "+c.getMinPlayers()+"+",CARD_SIZE));break;
        }
    }

    @Override
    public void visit(Farmer c) {
        switch(layer) {
            case 1:print(c, printMiddle("FARMER   ", CARD_SIZE));break;

            case 2:print(c, printMiddle("-" + c.getSustainDiscount() + "♣      ", CARD_SIZE));break;

            case 3:print(c, printMiddle("", CARD_SIZE));break;

            case 4: print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    public void visit(HuntEventCard c) {
        switch(layer) {
            case 1: print(c, printMiddle("HUNT EVENT     ", CARD_SIZE));break;

            case 2:print(c, printMiddle(c.getFoodBonus() + "♣+" + c.getPrestigePointsBonus() + " x hunter", CARD_SIZE));break;

            case 3:print(c, printMiddle("", CARD_SIZE));break;

            case 4:print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(SustainEventCard c) {
        switch(layer) {
            case 1:print(c, printMiddle("SUSTAIN EVENT ", CARD_SIZE));break;

            case 2:print(c, printMiddle("-1♣/" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 3:    print(c, printMiddle("  x charCard", CARD_SIZE));break;

            case 4:    print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(PaintingEventCard c) {
        switch(layer) {
            case 1:print(c, printMiddle("PAINTING EVENT ", CARD_SIZE));break;

            case 2: print(c, printMiddle(c.getMinArtist() + ": -" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 3:print(c, printMiddle(c.getMinArtist() + 1 + "+: " + c.getPrestigePointsBonus() + "x artist", CARD_SIZE));break;

            case 4:  print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(RitualEventCard c) {
        switch(layer) {
            case 1:  print(c, printMiddle("SHAMAN RITUAL ", CARD_SIZE));break;

            case 2:print(c, printMiddle("★>:  " + c.getPrestigePointsBonus(), CARD_SIZE));break;

            case 3: print(c, printMiddle("★<: -" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 4:  print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(EventCard c){
    }

    @Override
    public void visit(Inventor c) {
        switch(layer) {
            case 1:print(c, printMiddle("INVENTOR   ", CARD_SIZE)); break;

            case 2: print(c, printMiddle("", CARD_SIZE));break;

            case 3:print(c, printMiddle(c.getIcon().toString(), CARD_SIZE));break;

            case 4: print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    @Override
    public void visit(Builder c) {
        switch(layer) {
            case 1:  print(c, printMiddle("BUILDER   ", CARD_SIZE));break;

            case 2:   print(c, printMiddle("    -" + c.getBuildingDiscount() + "♣", CARD_SIZE));break;

            case 3:   print(c, printMiddle(c.getPrestigePoints() + "♦     ", CARD_SIZE));break;

            case 4: print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;

        }
    }

    @Override
    public void visit(Artist c) {
        switch(layer) {
            case 1: print(c, printMiddle("ARTIST   ", CARD_SIZE));break;

            case 2: print(c, printMiddle("", CARD_SIZE));break;

            case 3: print(c, printMiddle("", CARD_SIZE));break;

            case 4:  print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    @Override
    public void visit(BuildingCard c) {
        switch(layer) {
            case 1:  print(c, printMiddle("BUILDING   ", CARD_SIZE));break;

            case 2:  print(c, printMiddle("", CARD_SIZE) + c.getDescription());break;

            case 3:  print(c, printMiddle("", CARD_SIZE));break;

            case 4:  print(c, printMiddle("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
}