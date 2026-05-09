package it.polimi.ingsw.am31.am31.view.tui;
import it.polimi.ingsw.am31.am31.modelPackage.cardsFolder.Card;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;
import static org.fusesource.jansi.Ansi.Color.*;
public class TUIConfig {

    //Useful constants used in TUI
    public static final String GO_BACK_VALUE = "back";
    public static final String GO_BACK_STRING = "[type " + GO_BACK_VALUE + " to go back]";


    public static void printCard(Card c) {
        String cardId = c.getCardId();
        if (cardId == null) return;
       c.acceptVisit(new TuiPrintVisitor());
    }
}
