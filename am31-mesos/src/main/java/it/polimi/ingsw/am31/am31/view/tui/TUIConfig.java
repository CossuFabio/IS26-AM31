package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.*;
public class TUIConfig {

    //Useful constants used in TUI
    public static String GO_BACK_VALUE = "back";
    public static String GO_BACK_STRING = "[press " + GO_BACK_VALUE + " to go back]";
    public static String EMPTY_OFFER_VALUE = "EMPTY";




    public static void printCard(Card c) {
        String cardId = c.getCardId();
        if (cardId == null) return;

        if (cardId.startsWith("b")) { // Builders
            System.out.println(ansi().fg(Ansi.Color.MAGENTA).a(cardId+" "+c).reset());
            return;
        } else if (cardId.startsWith("s")) { // Shamans
            System.out.println(ansi().fg(Ansi.Color.CYAN).a(cardId+" "+c).reset());
            return;
        } else if (cardId.startsWith("h")) { // Hunters
            System.out.println(ansi().fg(Ansi.Color.RED).a(cardId+" "+c).reset());
            return;
        } else if (cardId.startsWith("i")) { //inventors
            System.out.println(ansi().fg(BLUE).a(cardId+" "+c).reset());
            return;
        } else if (cardId.startsWith("a")) { //artist
            System.out.println(ansi().fg(YELLOW).a(cardId+" "+c).reset());
            return;
        }
        else if (cardId.startsWith("f")){ //farmers
            System.out.println(ansi().fg(GREEN).a(cardId+" "+c).reset());
            return;
        }
        System.out.println((cardId)+" "+c);
        return ;
    }
}
