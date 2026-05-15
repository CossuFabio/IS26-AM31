package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.LocalState.LocalPlayerState;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;


import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

public class TUIConfig {

    //Useful constants used in TUI
    public static final String GO_BACK_VALUE = "back";
    public static final String SKIP_VALUE = "skip";
    public static final String GO_BACK_STRING = "[type " + GO_BACK_VALUE + " to return to previous screen]";
    public static final int SMALL_CARD_SIZE = 6;
    //offer track sizes
    public static final int SMALL_OFFER_CARD_PADDING = 2;
    public static final String SMALL_OFFER_CARD_SPACING = "  ";
    public static final String SMALL_OFFER_CARD_BORDER = " ";
    public static final String SMALL_OFFER_CARD_BOX = "   ";
    public static final int SMALL_OFFER_CARD_SIZE = SMALL_OFFER_CARD_PADDING + SMALL_OFFER_CARD_BOX.length()+ SMALL_OFFER_CARD_BORDER.length()*2+ SMALL_OFFER_CARD_SPACING.length();

    //full off.track sizes
    public static final int OFFER_CARD_PADDING = 3;
    public static final String OFFER_CARD_BOX = "     ";
    public static final String OFFER_CARD_BORDER = " ";
    public static final String OFFER_CARD_SPACING = "  ";
    public static final int OFFER_CARD_SIZE = OFFER_CARD_PADDING + OFFER_CARD_BOX.length()+ OFFER_CARD_BORDER.length()*2 + OFFER_CARD_SPACING.length();



    //prints the style of a card
    public static void printColor(Card c) {
        if (c == null || c.getCardId() == null) return;
        c.acceptVisit(new TuiPrintVisitor());
    }

    public static void printColor(LocalPlayerState p) {
        if (p == null ) return;
        switch(p.getColor()) {
            case RED:
                System.out.print(ansi().fg(Ansi.Color.RED).toString()); break;
            case YELLOW:
                System.out.print(ansi().fg(Ansi.Color.YELLOW).toString()); break;
            case BLUE:
                System.out.print(ansi().fg(Ansi.Color.BLUE).toString()); break;
            case BLACK:
                System.out.print(ansi().fg(Ansi.Color.WHITE).bg(Ansi.Color.BLACK).toString()); break;
            case WHITE:
                System.out.print(ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.WHITE).toString()); break;
        }
    }
    public static String getColor(LocalPlayerState p) {
        if (p == null ) return null;
        switch(p.getColor()) {
            case RED:
                return (ansi().fg(Ansi.Color.RED).toString());
            case YELLOW:
                return (ansi().fg(Ansi.Color.YELLOW).toString());
            case BLUE:
                return (ansi().fg(Ansi.Color.BLUE).toString());
            case BLACK:
                return (ansi().fg(Ansi.Color.WHITE).bg(Ansi.Color.BLACK).toString());
            case WHITE:
                return (ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.WHITE).toString());
        }
        return null;
    }
  //reset style
    public static void reset() {
        System.out.print(ansi().reset());
    }

    //prints s in the cards color
    public static void print(Card c, String s) {
        printColor(c);
        System.out.print(s);
        reset();
    }
    //prints s in the players color
    public static void print(LocalPlayerState p, String s) {
        printColor(p);
        System.out.print(s);
        reset();
    }

    //prints s in the specified color
    public static void print(Ansi.Color c, String s) {
        System.out.print(ansi().fg(c).a(s));
        reset();
    }

    //prints cards details in their color
    public static void printCard(Card c) {
        if (c == null || c.getCardId() == null) return;
        TuiPrintVisitor visitor = new TuiPrintVisitor();
        c.acceptVisit(visitor);
        System.out.print(c.getCardId() + ":- " + c);
        reset();
        System.out.print("\n");
        if (visitor.isBuilding()) {
            c.acceptVisit(visitor);
            BuildingCard c1 = (BuildingCard) c;
            System.out.print("Brief Description: " + c1.getDescription());
            reset();
            System.out.print("\n");
        }
    }
    //prints the stylized card line the game main screen
    public static void printCardLine(List<Card> cards) {
        for (Card c : cards) {
            print(c, printUpper(SMALL_CARD_SIZE));
        }
        System.out.println();
        for (Card c : cards) {
            print(c, printMiddle(c.getCardId(), SMALL_CARD_SIZE) );
        }
        System.out.println();
        for (Card c : cards) {
           print(c, printLower(SMALL_CARD_SIZE));
        }
        System.out.println();
    }
    //prints the stylized offer track for the main game screen
    public static void printOfferTrack(LocalGameState gamestate){
        List<LocalOfferCard> cards =gamestate.getBoard().getOfferTrack();
        //printing upper side
        for (LocalOfferCard c : cards) {
            print(Ansi.Color.DEFAULT, printUpper(SMALL_OFFER_CARD_SIZE));
        }
        System.out.println();
        //printing middle part
        for (LocalOfferCard c : cards) {
            //id in 4 chars
            String fixedId = StringUtils.rightPad(c.getOfferCardId(), SMALL_OFFER_CARD_PADDING);
            //if free prints white box, or green?
            String box;

            if (c.isFree())
                box = ansi().bg(Ansi.Color.DEFAULT).a(SMALL_OFFER_CARD_BOX).reset().toString();
            else    //if not free, prints box in the players color
                box = getColor(gamestate.findPlayer(c.getPlayer()))+"   "+ansi().reset().toString();
            //to be aligned, the sum of Box + fixedId + border-space is equal to the small card size
            System.out.print("┃"+ SMALL_OFFER_CARD_BORDER +fixedId+ SMALL_OFFER_CARD_SPACING +box+ SMALL_OFFER_CARD_BORDER +"┃");
        }
        System.out.println();
        //lower row
        for (LocalOfferCard c : cards) {
            print( Ansi.Color.DEFAULT, printLower(SMALL_OFFER_CARD_SIZE));
        }
        System.out.println();
    }


    //prints the upper side of a card, in the specified size
    public static String printUpper(int size){
        String midPiece = StringUtils.repeat("━", size);
        return "┏"+ midPiece + "┓";
    }

    //prints the lower side of a card, in the specified size
    public static String printLower(int size){
        String midPiece = StringUtils.repeat("━", size);
        return "┗"+ midPiece  +"┛";
    }

    //prints the string with the right spacing from the borders, in the middle of a card
    public static String printMiddle(String c, int size){
            return "┃"+ StringUtils.center(c,size," ") + "┃";
    }

}
