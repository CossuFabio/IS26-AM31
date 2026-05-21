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
    public static final int SMALL_CARD_SIZE = 8;
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
    public static final int CARD_SIZE = 15;
    public static final int TURNORDER_SIZE = 25; //can't be under 24


    //prints the style of a card
    public static void printColor(Card c) {
        if (c == null || c.getCardId() == null) return;
        c.acceptVisit(new TuiColorVisitor());
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
        TuiColorVisitor visitor = new TuiColorVisitor();
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
            print(c, upperBorder(SMALL_CARD_SIZE));
        }
        System.out.println();
        for (Card c : cards) {
            print(c, insideBorder(c.getCardId(), SMALL_CARD_SIZE) );
        }
        System.out.println();
        for (Card c : cards) {
           print(c, lowerBorder(SMALL_CARD_SIZE));
        }
        System.out.println();
    }
    //prints the stylized offer track for the main game screen
    public static void printOfferTrack(LocalGameState gamestate){
        List<LocalOfferCard> cards =gamestate.getBoard().getOfferTrack();
        //printing upper side
        for (LocalOfferCard c : cards) {
            print(Ansi.Color.DEFAULT, upperBorder(SMALL_OFFER_CARD_SIZE));
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
            print( Ansi.Color.DEFAULT, lowerBorder(SMALL_OFFER_CARD_SIZE));
        }
        System.out.println();
    }
    //prints a big version of a card
    public static void printDetailedCard (Card c) {
        TuiCardPrintVisitor visitor = new TuiCardPrintVisitor();

            reset();
            print(c, upperBorder(CARD_SIZE));
        System.out.println();
        //4 layers inside
        for(int i=0;i<4;i++) {
            c.acceptVisit(visitor); //draws the current layer
            visitor.nextLayer();
            System.out.println();
        }
        print(c, lowerBorder(CARD_SIZE));
        System.out.println();
    }
    //prints a big version of a card line
    public static void printDetailedCardLine (List<Card> cards) {
        TuiCardPrintVisitor visitor = new TuiCardPrintVisitor();
        for(Card c : cards) {
            reset();
            print(c, upperBorder(CARD_SIZE));
        }System.out.println();
        //4 layers inside
        for(Card c:cards) {
            c.acceptVisit(visitor); //draws the current layer
        }
        visitor.nextLayer();
        System.out.println();
        for(Card c:cards) {
            c.acceptVisit(visitor);
        }
        visitor.nextLayer();
        System.out.println();
        for(Card c:cards) {
            c.acceptVisit(visitor);
        }
        visitor.nextLayer();
        System.out.println();
        for(Card c:cards) {
            c.acceptVisit(visitor);
        }
        System.out.println();
        for(Card c : cards){
        print(c, lowerBorder(CARD_SIZE));
        }

    }
    //returns the correct bonus for the specified position with n players
    public static String turnOrderBonus (int nplayers, int pos) {
        switch (nplayers) {
            case 2:             switch(pos){
                case 1: return "1♣";
                case 2: return "-1♣/-2♦";
            } break;
            case 3:                switch(pos){
                case 1:return "2♣";
                case 2:return "";
                case 3:return "-1♣/♦";
            }break;
            case 4:                 switch(pos){
                case 1:return "2♣";
                case 2:return "1♣";
                case 3:return "";
                case 4:return "-1♣/-2♦";
            }break;
                case 5:                switch(pos){
                    case 1:return"3♣";
                    case 2:return"1♣";
                    case 3:return "";
                    case 4:return "";
                    case 5:return "-1♣/-2♦";
                }break;
        }
        return "";
    }



    //prints the turn order Card, called in the detailed offertrack screen
    public static void printTurnOrder(LocalGameState gameState, String local){
    System.out.print(upperBorder(TURNORDER_SIZE));
    System.out.println();
    int i = 0;
    int nplayers = gameState.getPlayers().size();
    String entry = "";
    for(LocalPlayerState p : gameState.getTurnOrder()){
        i++;
        if(p == null){  //print the string anyway without the player
            entry = i+"- "+turnOrderBonus(nplayers,i);
            int total = TURNORDER_SIZE - entry.length();
            int left = total / 2;
            int right = total - left;

            String riga = "┃" + " ".repeat(left) + entry + ansi().reset() + " ".repeat(right) + "┃";
            System.out.println(riga);
            continue;
        }
        else if(p.getNickname().equals(local))
            entry = i + "- YOU - " + p.getColor()+" "+turnOrderBonus(nplayers,i)+" ";
        else entry = i + "- " + p.getColor()+" "+turnOrderBonus(nplayers,i)+" ";
        // counting spaces
        int total = TURNORDER_SIZE - entry.length();
        int left = total / 2;
        int right = total - left;

        String riga = "┃" + " ".repeat(left) + getColor(p) + entry + ansi().reset() + " ".repeat(right) + "┃";
        System.out.println(riga);
    }
    System.out.print(lowerBorder(TURNORDER_SIZE));
    System.out.println();


    }

    public static void printDetailedOfferTrack (LocalGameState gameState) {
        List<LocalOfferCard> cards = gameState.getBoard().getOfferTrack();
        System.out.println(ansi().reset());
        //upper side
        for (LocalOfferCard c : cards)
            System.out.print(upperBorder(OFFER_CARD_SIZE));
        System.out.println();
        //inside
        for (LocalOfferCard c : cards) {
            String fixedId = StringUtils.rightPad(c.getOfferCardId(), OFFER_CARD_PADDING);
            System.out.print(insideBorder(fixedId+OFFER_CARD_BORDER + OFFER_CARD_BOX,OFFER_CARD_SIZE));
        }
        System.out.println();
        //inside 2
        for (LocalOfferCard c : cards)
            if (c.getFood() > 0)
                System.out.print(insideBorder("Gives " + c.getFood() + "♣", OFFER_CARD_SIZE));
            else
                System.out.print(insideBorder((StringUtils.repeat("↓", c.getDrawFromUnder()) + StringUtils.repeat("↑", c.getDrawFromUpper())), OFFER_CARD_SIZE));
        System.out.println();
        //inside 3 to draw box
        for (LocalOfferCard c : cards) {
            String box = "";
            if (c.isFree()) box = ansi().bg(Ansi.Color.DEFAULT).a(OFFER_CARD_BOX).reset().toString();
            else box = getColor(gameState.findPlayer(c.getPlayer())) + OFFER_CARD_BOX + ansi().reset().toString();
            System.out.print("┃" + OFFER_CARD_BORDER + OFFER_CARD_SPACING + box + OFFER_CARD_BORDER + OFFER_CARD_SPACING + SMALL_OFFER_CARD_BORDER + "┃");

        }
        System.out.println();
        //inside 4 to draw box
        for (LocalOfferCard c : cards) {
            String box = "";
            if (c.isFree()) box = ansi().bg(Ansi.Color.DEFAULT).a(OFFER_CARD_BOX).reset().toString();
            else box = getColor(gameState.findPlayer(c.getPlayer())) + OFFER_CARD_BOX + ansi().reset().toString();
            System.out.print("┃" + OFFER_CARD_BORDER + OFFER_CARD_SPACING + box + OFFER_CARD_BORDER + OFFER_CARD_SPACING + SMALL_OFFER_CARD_BORDER + "┃");
        }
        System.out.println();
        //lower
        for (LocalOfferCard c : cards)
            System.out.print(lowerBorder(OFFER_CARD_SIZE));
    }


    //prints the upper side of a card, in the specified size
    public static String upperBorder(int size){
        String midPiece = StringUtils.repeat("━", size);
        return "┏"+ midPiece + "┓";
    }

    //prints the lower side of a card, in the specified size
    public static String lowerBorder(int size){
        String midPiece = StringUtils.repeat("━", size);
        return "┗"+ midPiece  +"┛";
    }

    //prints the string with the right spacing from the borders, in the middle of a card
    public static String insideBorder(String c, int size){
            return "┃"+ StringUtils.center(c,size," ") + "┃";
    }

}
