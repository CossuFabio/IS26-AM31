package it.polimi.ingsw.am31.am31.view.tui;

import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.eventCards.*;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.TribeVisitor;
import org.apache.commons.lang3.StringUtils;

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
                    print(c, insideBorder(" "+c.getCardId()+" "+"HUNTER ♦ ", CARD_SIZE));
                else print(c, insideBorder(" "+c.getCardId()+" "+"HUNTER ", CARD_SIZE));
                 break;
            case 2: print(c, insideBorder("", CARD_SIZE));
                 break;
            case 3: print(c, insideBorder("", CARD_SIZE));
                 break;
            case 4: print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));
                 break;
        }
    }

    @Override
    public void visit(Shaman c) {
        switch(layer){
            case 1: print(c, insideBorder(" "+c.getCardId()+" "+"SHAMAN ",CARD_SIZE));break;

            case 2: print(c, insideBorder(StringUtils.repeat("★",c.getStars()),CARD_SIZE));break;

            case 3:print(c, insideBorder("",CARD_SIZE));break;

            case 4:print(c, insideBorder("era "+c.getEra()+"  "+c.getMinPlayers()+"+",CARD_SIZE));break;
        }
    }

    @Override
    public void visit(Farmer c) {
        switch(layer) {
            case 1:print(c, insideBorder(" "+c.getCardId()+" "+"FARMER  ", CARD_SIZE));break;

            case 2:print(c, insideBorder("-" + c.getSustainDiscount() + "♣      ", CARD_SIZE));break;

            case 3:print(c, insideBorder("", CARD_SIZE));break;

            case 4: print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    public void visit(HuntEventCard c) {
        switch(layer) {
            case 1: print(c, insideBorder("HUNT EVENT", CARD_SIZE));break;

            case 2:print(c, insideBorder(c.getFoodBonus() + "♣+" + c.getPrestigePointsBonus() + " x hunter", CARD_SIZE));break;

            case 3:print(c, insideBorder("", CARD_SIZE));break;

            case 4:print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(SustainEventCard c) {
        switch(layer) {
            case 1:print(c, insideBorder("SUSTAIN EVENT", CARD_SIZE));break;

            case 2:print(c, insideBorder("-1♣/" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 3:    print(c, insideBorder("  x charCard", CARD_SIZE));break;

            case 4:    print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(PaintingEventCard c) {
        switch(layer) {
            case 1:print(c, insideBorder("PAINTING EVENT", CARD_SIZE));break;

            case 2: print(c, insideBorder(c.getMinArtist() + ": -" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 3:print(c, insideBorder(c.getMinArtist() + 1 + "+: " + c.getPrestigePointsBonus() + "x artist", CARD_SIZE));break;

            case 4:  print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(RitualEventCard c) {
        switch(layer) {
            case 1:  print(c, insideBorder("SHAMAN RITUAL", CARD_SIZE));break;

            case 2:print(c, insideBorder("★>:  " + c.getPrestigePointsBonus(), CARD_SIZE));break;

            case 3: print(c, insideBorder("★<: -" + c.getPrestigePointsMalus(), CARD_SIZE));break;

            case 4:  print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
    public void visit(EventCard c){
    }

    @Override
    public void visit(Inventor c) {
        switch(layer) {
            case 1:print(c, insideBorder(" "+c.getCardId()+" "+"INVENTOR ", CARD_SIZE)); break;

            case 2: print(c, insideBorder("", CARD_SIZE));break;

            case 3:print(c, insideBorder(c.getIcon().toString(), CARD_SIZE));break;

            case 4: print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    @Override
    public void visit(Builder c) {
        switch(layer) {
            case 1:  print(c, insideBorder(" "+c.getCardId()+" "+"BUILDER ", CARD_SIZE));break;

            case 2:   print(c, insideBorder("    -" + c.getBuildingDiscount() + "♣", CARD_SIZE));break;

            case 3:   print(c, insideBorder(c.getPrestigePoints() + "♦     ", CARD_SIZE));break;

            case 4: print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;

        }
    }

    @Override
    public void visit(Artist c) {
        switch(layer) {
            case 1: print(c, insideBorder(" "+c.getCardId()+" "+"ARTIST ", CARD_SIZE));break;

            case 2: print(c, insideBorder("", CARD_SIZE));break;

            case 3: print(c, insideBorder("", CARD_SIZE));break;

            case 4:  print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }

    @Override
    public void visit(BuildingCard c) {
        switch(layer) {
            case 1:  print(c, insideBorder(" "+c.getCardId()+" "+"BUILDING ", CARD_SIZE));break;

            case 2:  print(c, insideBorder("Cost:"+c.getCost()+"♣", CARD_SIZE) + StringUtils.trim(c.getDescription()));break;
        //long descriptions won't be visualized correctly
            case 3:  print(c, insideBorder("Gives:"+c.getPrestigePointsGained()+"♦", CARD_SIZE));break;

            case 4:  print(c, insideBorder("era " + c.getEra() + "  " + c.getMinPlayers() + "+", CARD_SIZE));break;
        }
    }
}