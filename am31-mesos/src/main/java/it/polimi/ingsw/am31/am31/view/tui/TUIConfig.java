package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.characterCards.CharacterCard;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.visitor.CountVisitor;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiColors;

import static org.fusesource.jansi.Ansi.ansi;

public class TUIConfig {

    //Useful constants used in TUI
    public static final String GO_BACK_VALUE = "back";
    public static final String SKIP_VALUE = "skip";
    public static final String GO_BACK_STRING = "[type " + GO_BACK_VALUE + " to go back]";

    //prints the formatting of a card
    public static void printColor(Card c) {
        if (c==null || c.getCardId() == null) return;
        c.acceptVisit(new TuiPrintVisitor());
    }

    public static void reset(){
        System.out.print( ansi().reset());
    }
    //prints s in the card color
    public static void print(Card c,String s){
        printColor(c);
        System.out.print(s);
        reset();
    }
    //prints s in the specified color
    public static void print(Ansi.Color c, String s){
        System.out.print(ansi().fg(c).a(s));
        reset();
    }
    //prints cards details in their color
    public static void printCard(Card c){
        if(c == null || c.getCardId() == null) return;
        TuiPrintVisitor visitor = new TuiPrintVisitor();
        c.acceptVisit(visitor);
        System.out.println(c.getCardId()+":- "+c);
        if(visitor.isBuilding()){
             BuildingCard c1 = (BuildingCard) c;
             System.out.println("Brief Description: "+c1.getDescription());
            }
        reset();
        }
        public static void printCardId(Card c){
        System.out.print("");
        print(c,c.getCardId());
        }
    }
