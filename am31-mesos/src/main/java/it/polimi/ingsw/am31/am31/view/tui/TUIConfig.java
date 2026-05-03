package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.*;
public class TUIConfig {

    //Useful constants used in TUI
    public static String GO_BACK_VALUE = "back";
    public static String GO_BACK_STRING = "[press " + GO_BACK_VALUE + " to go back]";



    public static String printCard(Card c) {
        String cardId = c.getCardId();
        if (cardId == null) return "";

        if (cardId.startsWith("b_")) { // Builders
            return ansi().fg(MAGENTA).a(c).reset().toString();

        } else if (cardId.startsWith("s_")) { // Shamans
            return ansi().fg(RED).a(c).reset().toString();

        } else if (cardId.startsWith("h_")) { // Hunters
            return ansi().fg(RED).a(c).reset().toString();

        } else if (cardId.startsWith("i_")) { //inventors
            return ansi().fg(BLUE).a(c).reset().toString();

        } else if (cardId.startsWith("p_")) { //painters
            return ansi().fg(YELLOW).a(c).reset().toString();
        }
        else if (cardId.startsWith("f_")){ //farmers
            return ansi().fg(GREEN).a(c).reset().toString();
        }
        return c.toString();
    }
}
