package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.buildingCards.BuildingCard;
import it.polimi.ingsw.am31.am31.view.localState.LocalGameState;
import it.polimi.ingsw.am31.am31.view.localState.LocalOfferCard;
import it.polimi.ingsw.am31.am31.view.localState.LocalPlayerState;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.Ansi;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class TUIConfig {

    //symbols
    public static final String FOOD = "♣";
    public static final String POINTS = "♦";

    //Useful constants used in TUI
    public static final String GO_BACK_VALUE = "back";
    public static final String SKIP_VALUE = "skip";
    public static final String GO_BACK_STRING = "[type " + GO_BACK_VALUE + " to return to previous screen]";
    public static final int SMALL_CARD_SIZE = 8;
    //offer track sizes
    public static final int SMALL_OFFER_CARD_PADDING = 2;
    public static final String SMALL_OFFER_CARD_SPACING = "  ";
    public static final String SMALL_OFFER_CARD_BORDER = " ";
    public static final String SMALL_OFFER_CARD_BOX = "ooo";
    public static final String SMALL_FREE_OFFER_CARD_BOX = "   ";

    public static final int SMALL_OFFER_CARD_SIZE = SMALL_OFFER_CARD_PADDING + SMALL_OFFER_CARD_BOX.length()+ SMALL_OFFER_CARD_BORDER.length()*2+ SMALL_OFFER_CARD_SPACING.length();

    //full off.track sizes
    public static final int OFFER_CARD_PADDING = 3;
    public static final int OFFER_CARD_BOX_SPACES = 5;
    public static final String OFFER_CARD_BOX = "o".repeat(OFFER_CARD_BOX_SPACES);
    public static final String FREE_OFFER_CARD_BOX = " ".repeat(OFFER_CARD_BOX_SPACES);


    public static final String OFFER_CARD_BORDER = " ";
    public static final String OFFER_CARD_SPACING = "  ";
    public static final int OFFER_CARD_SIZE = OFFER_CARD_PADDING + OFFER_CARD_BOX.length()+ OFFER_CARD_BORDER.length()*2 + OFFER_CARD_SPACING.length();
    public static final int CARD_SIZE = 15;
    public static final int TURNORDER_SIZE = 25; //can't be under 24
    public static final int RESULT_TITLE_SIZE = 18;
    public static final int RANKING_COL_SIZE = 18;

    //prints the style of a card
    public static void printColor(Card c) {
        if (c == null || c.getCardId() == null) return;
        c.acceptVisit(new TuiColorVisitor());
    }

    /**
     * <p>Prints the p player hansi color</p>
     * @param p The player whose "color" filed is used to decide the formatting. If invalid player, resets formatting.
     */
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
            default:  reset(); break;
        }

    }

    /**
     * <p>Returns the hansi code for the player p color</p>
     * @param p Player whose "color" filed is used to determine the formatting string. if invalid color, resets formatting
     * @return
     */
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
            default:  return ansi().reset().toString();
        }
    }
  //reset style

    /**
     * <p>Resets the style of System out print to default</p>
     */
    public static void reset() {
        System.out.print(ansi().reset());
    }

    //prints s in the cards color

    /**
     * <p>Prints the S string in the Format decided by the Card c</p>
     * @param c Used to determine the format
     * @param s Printed in the selected card's format
     */
    public static void print(Card c, String s) {
        printColor(c);
        System.out.print(s);
        reset();
    }

    /**
     * <p>Prints the string s in the players color formatting</p>
     * @param p Player whose "color" filed is used to determine the formatting string. if invalid color, resets formatting
     * @param s Printed String
     */
    public static void print(LocalPlayerState p, String s) {
        printColor(p);
        System.out.print(s);
        reset();
    }

    /**
     *
     * @param c Chosen color form print
     * @param s Printed String
     */
    public static void print(Ansi.Color c, String s) {
        System.out.print(ansi().fg(c).a(s));
        reset();
    }

    /**
     *<p>Prints Card c details in the correct format</p>
     * @param c Used to determine the formatting and the details. if a building, description is added
     */
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

    /**
     *<p>Prints the stylized card line the game main screen</p>
     * @param cards List to be printed, imitating a real card shape
     */
    public static void printCardLine(List<Card> cards) {
        for (Card c : cards) {
            print(c, upperBorder(SMALL_CARD_SIZE));
        }
        System.out.println();
        for (Card c : cards) {
            print(c, centeredInsideBorder(c.getCardId(), SMALL_CARD_SIZE) );
        }
        System.out.println();
        for (Card c : cards) {
           print(c, lowerBorder(SMALL_CARD_SIZE));
        }
        System.out.println();
    }

    /**
     *Prints the stylized offer track for the main game screen
     * @param gamestate State of the game at the moment
     */
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
                box = ansi().bg(Ansi.Color.DEFAULT).a(SMALL_FREE_OFFER_CARD_BOX).reset().toString();
            else    //if not free, prints box in the players color
                box = getColor(gamestate.findPlayer(c.getPlayer()))+SMALL_OFFER_CARD_BOX+ansi().reset().toString();
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
    public static void printOrderdTribe(LocalGameState gameState) {
        //names + scores
        for (LocalPlayerState p : gameState.getPlayers()) {
            print(p,p.toString());System.out.println();
        }
        //then each one with their tribe and buildings
        for (LocalPlayerState p : gameState.getPlayers()) {
            System.out.println("\n");
            print(p,p.getNickname());
            System.out.println();
            //sorted using their id (require by the rules?)
            Map<Character, List<Card>> orderedTribe = p.getTribe().stream().collect(Collectors.groupingBy(c -> c.getCardId().charAt(0)));
            for (Map.Entry<Character, List<Card>> entry : orderedTribe.entrySet())
            {
                printDetailedCardLine(entry.getValue());
                System.out.println(ansi().reset());
            }
            System.out.println();
            printDetailedCardLine(p.getBuildings());
            System.out.println();
        }
        ansi().reset();
    }




    //prints a big version of a card line
    public static void printDetailedCardLine (List<Card> cards) {
        TuiCardPrintVisitor visitor = new TuiCardPrintVisitor();
        for(Card c : cards) {
            reset();
            print(c, upperBorder(CARD_SIZE));System.out.print(" ");
        }System.out.println();
        //4 layers inside
        for(Card c:cards) {
            c.acceptVisit(visitor); //draws the current layer
            System.out.print(" ");
        }
        visitor.nextLayer();
        System.out.println();
        for(Card c:cards) {
            c.acceptVisit(visitor);
            System.out.print(" ");
        }
        visitor.nextLayer();
        System.out.println();
        for(Card c:cards) {
            c.acceptVisit(visitor);
            System.out.print(" ");
        }
        visitor.nextLayer();
        System.out.println();
        for(Card c:cards) {
            c.acceptVisit(visitor);
            System.out.print(" ");
        }
        System.out.println();
        for(Card c : cards){
        print(c, lowerBorder(CARD_SIZE));
            System.out.print(" ");
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
                case 3:return "-1♣/-2♦";
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
            System.out.print(centeredInsideBorder(fixedId+OFFER_CARD_BORDER + FREE_OFFER_CARD_BOX,OFFER_CARD_SIZE));
        }
        System.out.println();
        //inside 2
        for (LocalOfferCard c : cards)
            if (c.getFood() > 0)
                System.out.print(centeredInsideBorder("Gives " + c.getFood() + "♣", OFFER_CARD_SIZE));
            else
                System.out.print(centeredInsideBorder((StringUtils.repeat("↓", c.getDrawFromUnder()) + StringUtils.repeat("↑", c.getDrawFromUpper())), OFFER_CARD_SIZE));
        System.out.println();
        //inside 3 to draw box
        for (LocalOfferCard c : cards) {
            String box = "";
            if (c.isFree()) box = ansi().bg(Ansi.Color.DEFAULT).a(FREE_OFFER_CARD_BOX).reset().toString();
            else box = getColor(gameState.findPlayer(c.getPlayer())) + OFFER_CARD_BOX + ansi().reset().toString();
            System.out.print("┃" + OFFER_CARD_BORDER + OFFER_CARD_SPACING + box + OFFER_CARD_BORDER + OFFER_CARD_SPACING + SMALL_OFFER_CARD_BORDER + "┃");

        }
        System.out.println();
        //inside 4 to draw box
        for (LocalOfferCard c : cards) {
            String box = "";
            if (c.isFree()) box = ansi().bg(Ansi.Color.DEFAULT).a(FREE_OFFER_CARD_BOX).reset().toString();
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

    //prints a string with the right spacing from the borders, in the middle of a card
    public static String centeredInsideBorder(String c, int size){
        if(c.length()>size)
            c=c.substring(0,size); //if too long, truncate
        int length = c.codePointCount(0, c.length());
        int leftPad = (size-length)/2;
 //       return "┃"+ StringUtils.center(c,size," ") + "┃"; modified but didn't fix the half spacing issue
        return "┃"+" ".repeat(leftPad)+c+" ".repeat(size-length-leftPad)+ "┃";

    }

//my first attempt at a javadoc annotation
    /**
     * <p>Prints the String c, centered inside a border of size "size",The string is in the color "color"
     * while the border is the default color</p>
     * @param c
     * @param size
     * @param color
     * @return
     */
    public static String coloredInsideBorder(String c, int size, Ansi.Color color){
        if(c.length()>size)
            c = c.substring(0, size);
        int leftPad = (size-c.length())/2;
            return "┃"+" ".repeat(leftPad)+ansi().fg(color)+c+ansi().reset()+" ".repeat(size-c.length()-leftPad)+ "┃";
    }

    //

}
